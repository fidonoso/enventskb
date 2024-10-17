import { DataTypes } from "sequelize";
import { sequelize } from "../database/database.js";
import User from "./Users.js";  // Asegúrate de tener definido el modelo Users

const Contact = sequelize.define(
  "Contact",
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
      validate: {
        isEmail: true,  // Validación de correo electrónico
      },
    },
    description: {
      type: DataTypes.STRING,
      allowNull: true, // Puede ser opcional
    },
 
  },
  {
    tableName: "contacts",  // Nombre de la tabla en la base de datos
    freezeTableName: true,  // Para evitar la pluralización automática del nombre de la tabla
    timestamps: false,      // No manejar los campos `createdAt` y `updatedAt`
  }
);

// Relación con el modelo User
Contact.belongsTo(User, { foreignKey: "user_id", as: "user" });

export default Contact;
