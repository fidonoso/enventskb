import { DataTypes } from "sequelize";
import { sequelize } from "../database/database.js";

const Invitado = sequelize.define(
  "Invitado",
  {
    id: {
      type: DataTypes.UUID,
      defaultValue: DataTypes.UUIDV4,
      primaryKey: true,
    },
    nombre: {
      type: DataTypes.STRING,
      allowNull: false,
    },
    email: {
      type: DataTypes.STRING,
      allowNull: false,
    },
    event_id: {
      type: DataTypes.UUID,
      allowNull: false,
    },
  },
  {
    tableName: "invitados", // Nombre de la tabla en la base de datos
    freezeTableName: true, // Para evitar pluralización del nombre de la tabla
    timestamps: false,
  }
);

export default Invitado;
