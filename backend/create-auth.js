const fs = require('fs');
const path = require('path');

const files = {
  // ================= USERS MODULE =================
  'src/users/dto/update-profile.dto.ts': `
import { IsOptional, IsString } from 'class-validator';

export class UpdateProfileDto {
  @IsOptional()
  @IsString()
  phoneNumber?: string;

  @IsOptional()
  @IsString()
  address?: string;
}
  `,

  'src/users/users.service.ts': `
import { Injectable, ConflictException } from '@nestjs/common';
import { PrismaService } from '../database/prisma.service';
import { Prisma, User, Role } from '@prisma/client';

@Injectable()
export class UsersService {
  constructor(private prisma: PrismaService) {}

  async findByEmail(email: string): Promise<User | null> {
    return this.prisma.user.findUnique({ where: { email } });
  }

  async findByGoogleId(googleId: string): Promise<User | null> {
    return this.prisma.user.findUnique({ where: { googleId } });
  }

  async findById(id: string): Promise<User | null> {
    return this.prisma.user.findUnique({
      where: { id },
      include: { profile: true },
    });
  }

  async createLocalUser(data: { email: string; passwordHash: string; role?: Role }): Promise<User> {
    const existing = await this.findByEmail(data.email);
    if (existing) {
      throw new ConflictException('User with this email already exists');
    }
    return this.prisma.user.create({
      data: {
        email: data.email,
        passwordHash: data.passwordHash,
        role: data.role || 'USER',
        profile: {
          create: {} // Create an empty profile by default
        }
      },
    });
  }

  async createGoogleUser(data: { email: string; googleId: string }): Promise<User> {
    return this.prisma.user.create({
      data: {
        email: data.email,
        googleId: data.googleId,
        role: 'USER',
        profile: {
          create: {}
        }
      },
    });
  }

  async updateProfile(userId: string, data: { phoneNumber?: string; address?: string }) {
    return this.prisma.userProfile.update({
      where: { userId },
      data,
    });
  }
}
  `,

  'src/users/users.controller.ts': `
import { Controller, Get, Body, Patch, UseGuards } from '@nestjs/common';
import { UsersService } from './users.service';
import { JwtAuthGuard } from '../auth/guards/jwt-auth.guard';
import { CurrentUser } from '../auth/decorators/current-user.decorator';
import { UpdateProfileDto } from './dto/update-profile.dto';

@UseGuards(JwtAuthGuard)
@Controller('users')
export class UsersController {
  constructor(private readonly usersService: UsersService) {}

  @Get('me')
  async getProfile(@CurrentUser() user: any) {
    const fullUser = await this.usersService.findById(user.id);
    if (!fullUser) return null;
    const { passwordHash, ...result } = fullUser;
    return result;
  }

  @Patch('me/profile')
  async updateProfile(@CurrentUser() user: any, @Body() updateData: UpdateProfileDto) {
    return this.usersService.updateProfile(user.id, updateData);
  }
}
  `,

  'src/users/users.module.ts': `
import { Module } from '@nestjs/common';
import { UsersService } from './users.service';
import { UsersController } from './users.controller';

@Module({
  controllers: [UsersController],
  providers: [UsersService],
  exports: [UsersService],
})
export class UsersModule {}
  `,

  // ================= AUTH DECORATORS & GUARDS =================
  'src/auth/decorators/roles.decorator.ts': `
import { SetMetadata } from '@nestjs/common';
import { Role } from '@prisma/client';

export const ROLES_KEY = 'roles';
export const Roles = (...roles: Role[]) => SetMetadata(ROLES_KEY, roles);
  `,

  'src/auth/decorators/current-user.decorator.ts': `
import { createParamDecorator, ExecutionContext } from '@nestjs/common';

export const CurrentUser = createParamDecorator(
  (data: unknown, ctx: ExecutionContext) => {
    const request = ctx.switchToHttp().getRequest();
    return request.user;
  },
);
  `,

  'src/auth/guards/jwt-auth.guard.ts': `
import { Injectable, ExecutionContext, UnauthorizedException } from '@nestjs/common';
import { AuthGuard } from '@nestjs/passport';

@Injectable()
export class JwtAuthGuard extends AuthGuard('jwt') {
  canActivate(context: ExecutionContext) {
    return super.canActivate(context);
  }

  handleRequest(err: any, user: any, info: any) {
    if (err || !user) {
      throw err || new UnauthorizedException('Authentication token is missing or invalid');
    }
    return user;
  }
}
  `,

  'src/auth/guards/local-auth.guard.ts': `
import { Injectable } from '@nestjs/common';
import { AuthGuard } from '@nestjs/passport';

@Injectable()
export class LocalAuthGuard extends AuthGuard('local') {}
  `,

  'src/auth/guards/google-oauth.guard.ts': `
import { Injectable } from '@nestjs/common';
import { AuthGuard } from '@nestjs/passport';

@Injectable()
export class GoogleOauthGuard extends AuthGuard('google') {}
  `,

  'src/auth/guards/roles.guard.ts': `
import { Injectable, CanActivate, ExecutionContext } from '@nestjs/common';
import { Reflector } from '@nestjs/core';
import { ROLES_KEY } from '../decorators/roles.decorator';
import { Role } from '@prisma/client';

@Injectable()
export class RolesGuard implements CanActivate {
  constructor(private reflector: Reflector) {}

  canActivate(context: ExecutionContext): boolean {
    const requiredRoles = this.reflector.getAllAndOverride<Role[]>(ROLES_KEY, [
      context.getHandler(),
      context.getClass(),
    ]);
    if (!requiredRoles) {
      return true; // No roles restricted
    }
    const { user } = context.switchToHttp().getRequest();
    return requiredRoles.includes(user?.role);
  }
}
  `,

  // ================= AUTH DTOs =================
  'src/auth/dto/register.dto.ts': `
import { IsEmail, IsString, MinLength } from 'class-validator';

export class RegisterDto {
  @IsEmail()
  email: string;

  @IsString()
  @MinLength(8, { message: 'Password must be at least 8 characters long' })
  password: string;
}
  `,

  'src/auth/dto/login.dto.ts': `
import { IsEmail, IsString } from 'class-validator';

export class LoginDto {
  @IsEmail()
  email: string;

  @IsString()
  password: string;
}
  `,

  // ================= AUTH STRATEGIES =================
  'src/auth/strategies/jwt.strategy.ts': `
import { ExtractJwt, Strategy } from 'passport-jwt';
import { PassportStrategy } from '@nestjs/passport';
import { Injectable } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';

@Injectable()
export class JwtStrategy extends PassportStrategy(Strategy) {
  constructor(private configService: ConfigService) {
    super({
      jwtFromRequest: ExtractJwt.fromAuthHeaderAsBearerToken(),
      ignoreExpiration: false,
      secretOrKey: configService.get<string>('JWT_SECRET') || 'defaultSecretForDevOnly',
    });
  }

  async validate(payload: any) {
    return { id: payload.sub, email: payload.email, role: payload.role };
  }
}
  `,

  'src/auth/strategies/local.strategy.ts': `
import { Strategy } from 'passport-local';
import { PassportStrategy } from '@nestjs/passport';
import { Injectable, UnauthorizedException } from '@nestjs/common';
import { AuthService } from '../auth.service';

@Injectable()
export class LocalStrategy extends PassportStrategy(Strategy) {
  constructor(private authService: AuthService) {
    super({ usernameField: 'email' });
  }

  async validate(email: string, password: string): Promise<any> {
    const user = await this.authService.validateUser(email, password);
    if (!user) {
      throw new UnauthorizedException('Invalid credentials');
    }
    return user;
  }
}
  `,

  'src/auth/strategies/google.strategy.ts': `
import { PassportStrategy } from '@nestjs/passport';
import { Strategy, VerifyCallback } from 'passport-google-oauth20';
import { Injectable } from '@nestjs/common';
import { ConfigService } from '@nestjs/config';
import { AuthService } from '../auth.service';

@Injectable()
export class GoogleStrategy extends PassportStrategy(Strategy, 'google') {
  constructor(
    private configService: ConfigService,
    private authService: AuthService,
  ) {
    super({
      clientID: configService.get<string>('GOOGLE_CLIENT_ID') || 'mock-id',
      clientSecret: configService.get<string>('GOOGLE_CLIENT_SECRET') || 'mock-secret',
      callbackURL: configService.get<string>('GOOGLE_CALLBACK_URL') || 'http://localhost:3000/api/auth/google/callback',
      scope: ['email', 'profile'],
    });
  }

  async validate(accessToken: string, refreshToken: string, profile: any, done: VerifyCallback): Promise<any> {
    const { id, emails } = profile;
    const email = emails[0].value;
    
    try {
      const user = await this.authService.validateGoogleUser(email, id);
      done(null, user);
    } catch (err) {
      done(err, false);
    }
  }
}
  `,

  // ================= AUTH SERVICE & CONTROLLER =================
  'src/auth/auth.service.ts': `
import { Injectable, UnauthorizedException, ConflictException } from '@nestjs/common';
import { UsersService } from '../users/users.service';
import { JwtService } from '@nestjs/jwt';
import * as bcrypt from 'bcrypt';
import { RegisterDto } from './dto/register.dto';

@Injectable()
export class AuthService {
  constructor(
    private usersService: UsersService,
    private jwtService: JwtService
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
        data: { googleId }
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
      passwordHash: hashedPassword
    });
    
    const { passwordHash, ...result } = user;
    return result;
  }
}
  `,

  'src/auth/auth.controller.ts': `
import { Controller, Post, UseGuards, Body, Get, Req, Res, HttpStatus } from '@nestjs/common';
import { AuthService } from './auth.service';
import { LocalAuthGuard } from './guards/local-auth.guard';
import { RegisterDto } from './dto/register.dto';
import { GoogleOauthGuard } from './guards/google-oauth.guard';
import { CurrentUser } from './decorators/current-user.decorator';

@Controller('auth')
export class AuthController {
  constructor(private authService: AuthService) {}

  @Post('register')
  async register(@Body() registerDto: RegisterDto) {
    return this.authService.register(registerDto);
  }

  @UseGuards(LocalAuthGuard)
  @Post('login')
  async login(@CurrentUser() user: any) {
    return this.authService.login(user);
  }

  @UseGuards(GoogleOauthGuard)
  @Get('google')
  async googleAuth() {
    // Initiates Google OAuth flow
  }

  @UseGuards(GoogleOauthGuard)
  @Get('google/callback')
  async googleAuthRedirect(@CurrentUser() user: any, @Res() res: any) {
    const token = await this.authService.login(user);
    // Standard approach: redirect to frontend with token in query or secure cookie
    // Since we are building API, we'll return JSON for now, or redirect.
    // Assuming frontend URL in env, for simplicity returning JSON here,
    // though in production you'd redirect to \`frontend_url?token=\${token.access_token}\`
    return res.status(HttpStatus.OK).json(token);
  }
}
  `,

  'src/auth/auth.module.ts': `
import { Module } from '@nestjs/common';
import { AuthService } from './auth.service';
import { AuthController } from './auth.controller';
import { UsersModule } from '../users/users.module';
import { PassportModule } from '@nestjs/passport';
import { JwtModule } from '@nestjs/jwt';
import { ConfigModule, ConfigService } from '@nestjs/config';
import { LocalStrategy } from './strategies/local.strategy';
import { JwtStrategy } from './strategies/jwt.strategy';
import { GoogleStrategy } from './strategies/google.strategy';

@Module({
  imports: [
    UsersModule,
    PassportModule,
    JwtModule.registerAsync({
      imports: [ConfigModule],
      useFactory: async (configService: ConfigService) => ({
        secret: configService.get<string>('JWT_SECRET') || 'defaultSecretForDevOnly',
        signOptions: { expiresIn: '1h' },
      }),
      inject: [ConfigService],
    }),
  ],
  controllers: [AuthController],
  providers: [AuthService, LocalStrategy, JwtStrategy, GoogleStrategy],
  exports: [AuthService],
})
export class AuthModule {}
  `,
};

for (const [filepath, content] of Object.entries(files)) {
  const dir = path.dirname(filepath);
  if (!fs.existsSync(dir)) {
    fs.mkdirSync(dir, { recursive: true });
  }
  fs.writeFileSync(filepath, content.trim());
}

console.log('Successfully wrote auth and users modules');
