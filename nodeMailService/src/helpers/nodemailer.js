import nodemailer from 'nodemailer';
import conf from '../config.js'

export const sendMailToGuest=async(remitente, invitados, asunto, mensaje)=>{

    const transporter = nodemailer.createTransport({
        host: conf.SMTP_HOST,
        port: conf.SMTP_PORT,
        secure: false, // Use `true` for port 465, `false` for all other ports
        auth: {
            user: conf.MAIL_USERNAME,
            pass: conf.MAIL_PASSWORD
        }
    });

    const mailOptions = {
        from: remitente,
        to: invitados,
        subject: asunto,
        html: mensaje
    };
    try {
        await transporter.sendMail(mailOptions);
        return true;
    } catch (error) {
        console.log('error:', error)
        return false
    }
}