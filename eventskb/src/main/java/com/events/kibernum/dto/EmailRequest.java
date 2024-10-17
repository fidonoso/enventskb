package com.events.kibernum.dto;

import java.util.List;
import java.util.UUID;

public class EmailRequest {

    private String sender;              // Remitente
    private List<String> recipients;    // Lista de destinatarios
    private String subject;             // Asunto del correo
    private String message;             // Cuerpo del mensaje en formato HTML
    private UUID eventId;               // ID del evento


    // Constructor con parámetros
    public EmailRequest(String sender, List<String> recipients, String subject, String message,  UUID eventId) {
        this.sender = sender;
        this.recipients = recipients;
        this.subject = subject;
        this.message = message;
        this.eventId = eventId;
    }

    // Getters y Setters
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public List<String> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<String> recipients) {
        this.recipients = recipients;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

     public UUID getEventId() {
        return eventId;
    }

    public void setEventId(UUID eventId) {
        this.eventId = eventId;
    }
}

