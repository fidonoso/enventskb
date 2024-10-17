import { DataTypes } from "sequelize";
import { sequelize } from "../database/database.js";
import User from "./Users.js";  // Asegúrate de tener definido el modelo Users
import Invitado from "./Invitados.js";  // Asegúrate de tener definido el modelo Invitados

const Event = sequelize.define(
  "Event",
  {
    id: {
      type: DataTypes.UUID,
      defaultValue: DataTypes.UUIDV4,
      primaryKey: true,
    },
    title: {
      type: DataTypes.STRING,
      allowNull: false,
    },
    description: {
      type: DataTypes.STRING,
      allowNull: false,
    },
    location: {
      type: DataTypes.STRING,
      allowNull: true,
    },
    dateTime: {
      type: DataTypes.DATE, // Sequelize utiliza el tipo DATE para almacenar `LocalDateTime`
      allowNull: true,
    },
    // Si necesitas asociar el evento con un usuario (relación ManyToOne)
    userId: {
      type: DataTypes.UUID,
      allowNull: false,
    },
  },
  {
    tableName: "events", // Nombre de la tabla en la base de datos
    freezeTableName: true, // Para evitar pluralización del nombre de la tabla
    timestamps: false, // No manejar los campos `createdAt` y `updatedAt`
  }
);

// Relaciones
Event.belongsTo(User, { foreignKey: "userId", as: "user" }); // Relación ManyToOne con Users
Event.hasMany(Invitado, { foreignKey: "event_id", as: "invitados" }); // Relación OneToMany con Invitados

export default Event;
