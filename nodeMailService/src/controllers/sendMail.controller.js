import {sendMailToGuest} from '../helpers/nodemailer.js'
import User from "../models/Users.js";
import { Op } from "sequelize";
import Contact from "../models/Contacts.js"; 
import Invitado from "../models/Invitados.js";
import { logger } from '../helpers/winston.js';


export const sendEmail=async (req, res) => {
    const { sender, recipients, subject, message, eventId } = req.body;
    // console.log('req.body==>', req.body)
    const guestData=await searchEmailGuest(sender, recipients );
    const guestEmails=guestData.map(c=>c.email);
    const guestNames=guestData.map(c=>c.nombre);
    const senderEmails= await searchEmailSender(sender)
    // console.log('Guests=>',guestData)
    // console.log('sender=>',senderEmails)
    logger.info(`Correo enviado: ${new Date()}`)
  

    try {
        const result= await sendMailToGuest(senderEmails, guestEmails, subject, message);
        if(result){
        // if(true){
            // const newGuests=await create
            const dataToSave=guestData.map(x=>({...x, event_id:eventId}))
            const nuevosInvitados=await crearInvitados(dataToSave)
            // console.log('nuevosInvitados=>', nuevosInvitados)
            res.status(200).json({status:'ok'});
        }else {
            throw new Error('Email not sent');
        }
    } catch (error) {
        logger.error(error);
        res.status(500).status({status:'fail'});
    }
};

export const requestPracticing=async(req, res)=>{
    try {
        const { sender, recipients, subject, message } = req.body;
        // console.log('body=>', req.body)
        const result= await sendMailToGuest(sender, recipients, subject, message);
        if(result){
            res.status(200).json({status:'ok'});
            logger.info(`Correo enviado - Solicitud de participación: ${new Date()}`)
        }else {
            throw new Error('Email not sent');
        }
    } catch (error) {
        logger.error(error);
        res.status(500).status({status:'fail'});
    }
}

////////////

const searchEmailGuest=async(userId, contactIds)=>{

    try {
        const emails = await Contact.findAll({
          where: {
            user_id: userId,            // Filtrar por el user_id
            id: {
              [Op.in]: contactIds,     // Filtrar por los ids de contactos en el array
            },
          },
          attributes: ["email", "nombre"],        // Solo devolver los emails
        });
    
        return emails.map(contact => ({email: contact.email, nombre:contact.nombre})); // Devolver solo los emails en un array
      } catch (error) {
        console.error("Error al obtener los emails:", error);
        throw error;
      }

};

const searchEmailSender=async(userId)=>{

    try {
        const usuario = await User.findOne({
          where: {
            id: userId,  // Filtrar por el user_id
          },
          attributes: ["email"],  // Solo devolver el campo email
        });
    
        if (usuario) {
          return usuario.email;  // Retornar el email del usuario
        } else {
          throw new Error("Usuario no encontrado");
        }
      } catch (error) {
        console.error("Error al obtener el email del usuario:", error);
        throw error;
      }
}

const crearInvitados=async(invitadosArray)=> {
    try {
      if (!Array.isArray(invitadosArray) || invitadosArray.length === 0) {
        throw new Error("El array de invitados está vacío.");
      }
  
      // Extraer event_id y email de los invitados a insertar
      const condiciones = invitadosArray.map(inv => ({
        event_id: inv.event_id,
        email: inv.email,
      }));
  
      // Buscar los invitados existentes en la tabla con event_id y email combinados
      const invitadosExistentes = await Invitado.findAll({
        where: {
          [Op.or]: condiciones,
        },
      });
  
      // Filtrar los invitados que ya existen
      const invitadosFiltrados = invitadosArray.filter(inv => {
        return !invitadosExistentes.some(existente => 
          existente.event_id === inv.event_id && existente.email === inv.email
        );
      });
  
      // Si no hay nuevos registros después de filtrar
      if (invitadosFiltrados.length === 0) {
        throw new Error("No hay nuevos invitados para agregar.");
      }
  
      // Crear los nuevos invitados
      const nuevosInvitados = await Invitado.bulkCreate(invitadosFiltrados, {
        validate: true,
      });
  
    //   console.log("Invitados creados exitosamente:", nuevosInvitados);
      return nuevosInvitados;
    } catch (error) {
      console.error("Error al crear invitados:", error);
      throw error;
    }
  }