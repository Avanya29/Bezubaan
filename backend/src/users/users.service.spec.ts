import { Test, TestingModule } from '@nestjs/testing';
import { UsersService } from './users.service';
import { PrismaService } from '../database/prisma.service';
import { ConflictException } from '@nestjs/common';

describe('UsersService', () => {
  let service: UsersService;
  let prismaService: PrismaService;

  beforeEach(async () => {
    const mockPrismaService = {
      user: {
        findUnique: jest.fn(),
        create: jest.fn(),
      },
    };

    const module: TestingModule = await Test.createTestingModule({
      providers: [
        UsersService,
        { provide: PrismaService, useValue: mockPrismaService },
      ],
    }).compile();

    service = module.get<UsersService>(UsersService);
    prismaService = module.get<PrismaService>(PrismaService);
  });

  it('should create a local user if email is unique', async () => {
    (prismaService.user.findUnique as jest.Mock).mockResolvedValue(null);
    (prismaService.user.create as jest.Mock).mockResolvedValue({
      id: '1',
      email: 'test@test.com',
    } as any);

    const user = await service.createLocalUser({
      email: 'test@test.com',
      passwordHash: 'hash',
    });
    expect(user).toBeDefined();
    expect(prismaService.user.create).toHaveBeenCalled();
  });

  it('should throw ConflictException if email exists', async () => {
    (prismaService.user.findUnique as jest.Mock).mockResolvedValue({
      id: '1',
      email: 'test@test.com',
    } as any);

    await expect(
      service.createLocalUser({ email: 'test@test.com', passwordHash: 'hash' }),
    ).rejects.toThrow(ConflictException);
  });
});
