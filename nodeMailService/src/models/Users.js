import { DataTypes } from "sequelize";
import { sequelize } from "../database/database.js";

const User = sequelize.define(
  "User", // Nombre del modelo
  {
    id: {
      type: DataTypes.UUID,
      defaultValue: DataTypes.UUIDV4,
      primaryKey: true,
    },
    username: {
      type: DataTypes.STRING,
      allowNull: true,
    },
    password: {
      type: DataTypes.STRING,
      allowNull: false,
    },
    name: {
      type: DataTypes.STRING,
      allowNull: false,
    },
    last_name: {
      type: DataTypes.STRING,
      allowNull: true,
    },
    email: {
      type: DataTypes.STRING,
      allowNull: false,
      unique: true,
    },
  },
  {
    tableName: "users", // Especifica el nombre exacto de la tabla en la base de datos
    freezeTableName: true, // Evita que Sequelize cambie el nombre de la tabla a plural
    timestamps: false, // No queremos que Sequelize gestione los campos `createdAt` y `updatedAt`
  }
);

export default User;
