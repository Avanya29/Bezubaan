import { Controller, Get, Logger } from '@nestjs/common';
import { PrismaService } from '../database/prisma.service';

@Controller('health')
export class HealthController {
  private readonly logger = new Logger(HealthController.name);

  constructor(private readonly prisma: PrismaService) {}

  @Get()
  liveness() {
    return {
      status: 'ok',
      timestamp: new Date().toISOString(),
      service: 'bezubaan-api',
    };
  }

  @Get('ready')
  async readiness() {
    const checks: Record<string, { status: string; message?: string }> = {};

    // Check database
    try {
      await this.prisma.$queryRaw`SELECT 1`;
      checks.database = { status: 'ok' };
    } catch (error) {
      this.logger.error(
        'Database health check failed',
        error instanceof Error ? error.message : error,
      );
      checks.database = {
        status: 'error',
        message: 'Database connection failed',
      };
    }

    const allHealthy = Object.values(checks).every((c) => c.status === 'ok');

    return {
      status: allHealthy ? 'ok' : 'degraded',
      timestamp: new Date().toISOString(),
      service: 'bezubaan-api',
      checks,
    };
  }
}
