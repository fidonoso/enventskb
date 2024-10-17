import Sequelice from "sequelize";
import config from "../config.js";

export const sequelize = new Sequelice(
  config.POSTGRES_DATABASE,
  config.POSTGRES_USER,
  config.POSTGRES_PASSWORD,
  {
    host: config.POSTGRES_HOST, 
    port: config.POSTGRES_DB_PORT,
    dialect: "postgres",
    logging: false,
  }
);
