import { Test, TestingModule } from '@nestjs/testing';
jest.setTimeout(15000);
import { AuthService } from './auth.service';
import { UsersService } from '../users/users.service';
import { JwtService } from '@nestjs/jwt';
import * as bcrypt from 'bcrypt';

describe('AuthService', () => {
  let service: AuthService;
  let usersService: UsersService;
  let jwtService: JwtService;

  beforeEach(async () => {
    const mockUsersService = {
      findByEmail: jest.fn(),
      createLocalUser: jest.fn(),
      createGoogleUser: jest.fn(),
    };

    const mockJwtService = {
      sign: jest.fn(),
    };

    const module: TestingModule = await Test.createTestingModule({
      providers: [
        AuthService,
        { provide: UsersService, useValue: mockUsersService },
        { provide: JwtService, useValue: mockJwtService },
      ],
    }).compile();

    service = module.get<AuthService>(AuthService);
    usersService = module.get<UsersService>(UsersService);
    jwtService = module.get<JwtService>(JwtService);
  });

  it('should be defined', () => {
    expect(service).toBeDefined();
  });

  describe('validateUser', () => {
    it('should return user without passwordHash on successful validation', async () => {
      const password = 'testPassword';
      const hash = await bcrypt.hash(password, 10);
      const user = {
        id: '1',
        email: 'test@example.com',
        passwordHash: hash,
        role: 'USER',
      };

      (usersService.findByEmail as jest.Mock).mockResolvedValue(user as any);

      const result = await service.validateUser('test@example.com', password);

      expect(result).toBeDefined();
      expect(result.passwordHash).toBeUndefined();
      expect(result.email).toBe('test@example.com');
    });

    it('should return null on invalid password', async () => {
      const password = 'testPassword';
      const hash = await bcrypt.hash(password, 10);
      const user = {
        id: '1',
        email: 'test@example.com',
        passwordHash: hash,
        role: 'USER',
      };

      (usersService.findByEmail as jest.Mock).mockResolvedValue(user as any);

      const result = await service.validateUser(
        'test@example.com',
        'wrongPassword',
      );

      expect(result).toBeNull();
    });
  });

  describe('login', () => {
    it('should return an access token', async () => {
      const user = { id: '1', email: 'test@example.com', role: 'USER' };
      (jwtService.sign as jest.Mock).mockReturnValue('signed-token');

      const result = await service.login(user);

      expect(result.access_token).toBe('signed-token');
      expect(jwtService.sign).toHaveBeenCalledWith({
        email: user.email,
        sub: user.id,
        role: user.role,
      });
    });
  });

  describe('register', () => {
    it('should hash password and create local user', async () => {
      const dto = { email: 'new@example.com', password: 'password123' };
      (usersService.createLocalUser as jest.Mock).mockResolvedValue({
        id: '2',
        email: dto.email,
        passwordHash: 'hashed-password',
        role: 'USER',
      } as any);

      const result = await service.register(dto);

      expect(usersService.createLocalUser).toHaveBeenCalledWith({
        email: dto.email,
        passwordHash: expect.any(String),
      });
      expect((result as any).passwordHash).toBeUndefined();
      expect(result.email).toBe(dto.email);
    });
  });
});
