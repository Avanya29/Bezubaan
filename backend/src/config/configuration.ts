export default () => ({
  port: parseInt(process.env.PORT || '3000', 10),
  nodeEnv: process.env.NODE_ENV || 'development',
  database: {
    url: process.env.DATABASE_URL,
  },
  redis: {
    host: process.env.REDIS_HOST || 'localhost',
    port: parseInt(process.env.REDIS_PORT || '6379', 10),
  },
  cors: {
    origin: process.env.CORS_ORIGIN || '*',
  },
  swagger: {
    enabled: process.env.SWAGGER_ENABLED !== 'false',
  },
});
