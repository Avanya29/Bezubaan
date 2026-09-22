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
import { GoogleTokenDto } from './dto/google-token.dto';
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

  /**
   * Native mobile Google Sign-In (Android Credential Manager).
   * Receives the Google ID token, verifies it server-side, and returns a JWT.
   */
  @Post('google/token')
  async googleIdToken(@Body() dto: GoogleTokenDto) {
    return this.authService.validateGoogleIdToken(dto.idToken);
  }

  @UseGuards(GoogleOauthGuard)
  @Get('google')
  async googleAuth() {
    // Initiates Google OAuth flow (web browser redirect)
  }

  @UseGuards(GoogleOauthGuard)
  @Get('google/callback')
  async googleAuthRedirect(@CurrentUser() user: any, @Res() res: any) {
    const token = await this.authService.login(user);
    return res.status(HttpStatus.OK).json(token);
  }
}
