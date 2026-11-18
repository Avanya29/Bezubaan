import {
  WebSocketGateway,
  WebSocketServer,
  OnGatewayConnection,
  OnGatewayDisconnect,
  OnGatewayInit,
} from '@nestjs/websockets';
import { Server, Socket } from 'socket.io';
import { Logger } from '@nestjs/common';
import * as jwt from 'jsonwebtoken';
import { ConfigService } from '@nestjs/config';

@WebSocketGateway({
  cors: {
    origin: '*',
  },
})
export class EventsGateway
  implements OnGatewayInit, OnGatewayConnection, OnGatewayDisconnect
{
  @WebSocketServer()
  server: Server;

  private logger: Logger = new Logger('EventsGateway');
  // Map to store connected volunteer sockets: userId -> Socket
  private connectedVolunteers: Map<string, Socket> = new Map();

  constructor(private configService: ConfigService) {}

  afterInit(server: Server) {
    this.logger.log('WebSocket Gateway Initialized');
  }

  handleConnection(client: Socket, ...args: any[]) {
    try {
      // In Socket.IO v4, auth tokens are usually passed in handshake.auth
      let token = client.handshake.auth?.token;

      // Fallback for custom headers
      if (!token && client.handshake.headers['authorization']) {
        token = client.handshake.headers['authorization'].replace(
          'Bearer ',
          '',
        );
      }

      if (!token) {
        client.disconnect();
        return;
      }

      const secret =
        this.configService.get<string>('JWT_SECRET') ||
        'defaultSecretForDevOnly';
      const decoded = jwt.verify(token, secret) as any;

      // Track all connected responder sockets for emergency dispatch
      this.connectedVolunteers.set(decoded.sub, client);
      this.logger.log(
        `Responder Connected to Live Radar: ${decoded.sub} (role: ${decoded.role}, Socket: ${client.id})`,
      );
    } catch (err) {
      this.logger.error(`Connection error: ${err.message}`);
      client.disconnect();
    }
  }

  handleDisconnect(client: Socket) {
    this.logger.log(`Client Disconnected: ${client.id}`);
    // Remove from active responders if present
    for (const [userId, socket] of this.connectedVolunteers.entries()) {
      if (socket.id === client.id) {
        this.connectedVolunteers.delete(userId);
        this.logger.log(`Responder Disconnected: ${userId}`);
        break;
      }
    }
  }

  /**
   * Broadcast an emergency rescue to all connected active volunteers
   */
  broadcastEmergency(rescue: any) {
    this.logger.log(
      `Broadcasting EMERGENCY RESCUE [${rescue.id}] to all connected responders (Tracked: ${this.connectedVolunteers.size}).`,
    );
    // Broadcast to all connected sockets on the gateway
    this.server.emit('new_emergency_rescue', rescue);
  }
}
