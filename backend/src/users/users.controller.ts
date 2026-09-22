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
    
    // The Android app's UserDto expects a 'name' field, but the Prisma
    // schema currently doesn't store one. Fall back to email prefix for now.
    return {
      ...result,
      name: result.email.split('@')[0],
    };
  }

  @Patch('me/profile')
  async updateProfile(
    @CurrentUser() user: any,
    @Body() updateData: UpdateProfileDto,
  ) {
    return this.usersService.updateProfile(user.id, updateData);
  }
}
