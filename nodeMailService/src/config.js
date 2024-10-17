import { config } from "dotenv";
config();

const configurations = {
  PORT: process.env.PORT || 4001,
  POSTGRES_HOST: process.env.DB_HOST ||"localhost",
  POSTGRES_DATABASE: process.env.DB_NAME || "Eventskb",
  POSTGRES_USER: process.env.DB_USER || "postgres",
  POSTGRES_PASSWORD: process.env.DB_PASSWORD || "postgres",
  POSTGRES_DB_PORT: process.env.DB_PORT || "5433",
  SMTP_HOST: process.env.SMTP_HOST,
  SMTP_PORT: process.env.SMTP_PORT,
  MAIL_USERNAME: process.env.MAIL_USERNAME,
  MAIL_PASSWORD: process.env.MAIL_PASSWORD

};
export default configurations;
