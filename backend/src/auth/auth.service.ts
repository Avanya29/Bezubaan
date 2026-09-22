import { Injectable, Logger, UnauthorizedException } from '@nestjs/common';
import { UsersService } from '../users/users.service';
import { JwtService } from '@nestjs/jwt';
import { ConfigService } from '@nestjs/config';
import { OAuth2Client } from 'google-auth-library';
import * as bcrypt from 'bcrypt';
import { RegisterDto } from './dto/register.dto';

@Injectable()
export class AuthService {
  private readonly logger = new Logger(AuthService.name);
  private readonly googleClient: OAuth2Client;

  constructor(
    private usersService: UsersService,
    private jwtService: JwtService,
    private configService: ConfigService,
  ) {
    const clientId =
      this.configService.get<string>('GOOGLE_CLIENT_ID') || 'mock-id';
    this.googleClient = new OAuth2Client(clientId);
  }

  async validateUser(email: string, pass: string): Promise<any> {
    const user = await this.usersService.findByEmail(email);
    if (user && user.passwordHash) {
      const isMatch = await bcrypt.compare(pass, user.passwordHash);
      if (isMatch) {
        const { passwordHash, ...result } = user;
        return result;
      }
    }
    return null;
  }

  async validateGoogleUser(email: string, googleId: string) {
    let user = await this.usersService.findByEmail(email);
    if (!user) {
      user = await this.usersService.createGoogleUser({ email, googleId });
    } else if (!user.googleId) {
      // Link account
      await this.usersService['prisma'].user.update({
        where: { email },
        data: { googleId },
      });
      user.googleId = googleId;
    }
    return user;
  }

  /**
   * Verify a Google ID token received from Android Credential Manager.
   * Returns a JWT access token on success.
   */
  async validateGoogleIdToken(idToken: string) {
    try {
      const clientId = this.configService.get<string>('GOOGLE_CLIENT_ID');
      const ticket = await this.googleClient.verifyIdToken({
        idToken,
        audience: clientId,
      });

      const payload = ticket.getPayload();
      if (!payload || !payload.email) {
        throw new UnauthorizedException('Invalid Google token: no email');
      }

      const user = await this.validateGoogleUser(payload.email, payload.sub);
      this.logger.log(
        `Google sign-in successful for ${payload.email} (sub=${payload.sub})`,
      );
      return this.login(user);
    } catch (error: any) {
      this.logger.error(`Google token verification failed: ${error.message}`);
      throw new UnauthorizedException(
        'Google authentication failed: ' + error.message,
      );
    }
  }

  async login(user: any) {
    const payload = { email: user.email, sub: user.id, role: user.role };
    return {
      access_token: this.jwtService.sign(payload),
    };
  }

  async register(registerDto: RegisterDto) {
    const hashedPassword = await bcrypt.hash(registerDto.password, 10);
    const user = await this.usersService.createLocalUser({
      email: registerDto.email,
      passwordHash: hashedPassword,
    });

    const { passwordHash, ...result } = user;
    return result;
  }
}
