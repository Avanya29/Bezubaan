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
        token = client.handshake.headers['authorization'].replace('Bearer ', '');
      }

      if (!token) {
        client.disconnect();
        return;
      }

      const secret = this.configService.get<string>('JWT_SECRET') || 'defaultSecretForDevOnly';
      const decoded = jwt.verify(token, secret) as any;

      // Only track active volunteers for emergency dispatch
      if (decoded.role === 'VOLUNTEER') {
        this.connectedVolunteers.set(decoded.sub, client);
        this.logger.log(`Volunteer Connected: ${decoded.sub} (Socket: ${client.id})`);
      } else {
        this.logger.log(`Citizen Connected: ${decoded.sub} (Socket: ${client.id})`);
      }
    } catch (err) {
      this.logger.error(`Connection error: ${err.message}`);
      client.disconnect();
    }
  }

  handleDisconnect(client: Socket) {
    this.logger.log(`Client Disconnected: ${client.id}`);
    // Remove from active volunteers if present
    for (const [userId, socket] of this.connectedVolunteers.entries()) {
      if (socket.id === client.id) {
        this.connectedVolunteers.delete(userId);
        this.logger.log(`Volunteer Disconnected: ${userId}`);
        break;
      }
    }
  }

  /**
   * Broadcast an emergency rescue to all connected active volunteers
   */
  broadcastEmergency(rescue: any) {
    this.logger.log(`Broadcasting EMERGENCY RESCUE [${rescue.id}] to ${this.connectedVolunteers.size} active volunteers.`);
    // In a real production app, we would filter by geolocation (e.g. within 5km radius).
    // For now, we broadcast to all active volunteers.
    this.connectedVolunteers.forEach((socket, userId) => {
      socket.emit('new_emergency_rescue', rescue);
    });
  }
}
