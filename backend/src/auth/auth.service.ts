import { Injectable } from '@nestjs/common';
import { UsersService } from '../users/users.service';
import { JwtService } from '@nestjs/jwt';
import * as bcrypt from 'bcrypt';
import { RegisterDto } from './dto/register.dto';

@Injectable()
export class AuthService {
  constructor(
    private usersService: UsersService,
    private jwtService: JwtService,
  ) {}

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
