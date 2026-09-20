import {
  ValidationPipe as NestValidationPipe,
  ValidationPipeOptions,
} from '@nestjs/common';

/**
 * Pre-configured validation pipe for the application.
 * Strips unknown properties and transforms payloads to DTO instances.
 */
export function createValidationPipe(
  options?: ValidationPipeOptions,
): NestValidationPipe {
  return new NestValidationPipe({
    whitelist: true,
    forbidNonWhitelisted: true,
    transform: true,
    transformOptions: {
      enableImplicitConversion: true,
    },
    ...options,
  });
}
