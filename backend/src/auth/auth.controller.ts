import {
  Controller,
  Post,
  UseGuards,
  Body,
  Get,
  Res,
  HttpStatus,
} from '@nestjs/common';
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
    // though in production you'd redirect to `frontend_url?token=${token.access_token}`
    return res.status(HttpStatus.OK).json(token);
  }
}
