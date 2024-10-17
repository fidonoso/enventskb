package com.events.kibernum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class KibernumApplication {
	static {
        // Cargar las variables del archivo .env en un bloque estático
        Dotenv dotenv = Dotenv.configure().load();

		// Asignar las variables como propiedades del sistema
        System.setProperty("SERVER_APPLICATION_PORT", dotenv.get("SERVER_APPLICATION_PORT"));
        System.setProperty("DB_HOST", dotenv.get("DB_HOST"));
        System.setProperty("DB_NAME", dotenv.get("DB_NAME"));
        System.setProperty("DB_USER", dotenv.get("DB_USER"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
        System.setProperty("DB_PORT", dotenv.get("DB_PORT"));
        System.setProperty("TOKEN_SECRET_KEY", dotenv.get("TOKEN_SECRET_KEY"));
        System.setProperty("SMTP_HOST", dotenv.get("SMTP_HOST"));
        System.setProperty("SMTP_PORT", dotenv.get("SMTP_PORT"));
        System.setProperty("MAIL_USERNAME", dotenv.get("MAIL_USERNAME"));
        System.setProperty("MAIL_PASSWORD", dotenv.get("MAIL_PASSWORD"));
        System.setProperty("email-service.url", dotenv.get("email-service.url"));

        // Imprimir valores en consola para verificar
        // System.out.println("SERVER_APPLICATION_PORT: " + dotenv.get("SERVER_APPLICATION_PORT"));
        // System.out.println("DB_HOST: " + dotenv.get("DB_HOST"));
        // System.out.println("DB_NAME: " + dotenv.get("DB_NAME"));
        // System.out.println("DB_USER: " + dotenv.get("DB_USER"));
        // System.out.println("DB_PASSWORD: " + dotenv.get("DB_PASSWORD"));
        // System.out.println("DB_PORT: " + dotenv.get("DB_PORT"));
        // System.out.println("TOKEN_SECRET_KEY: " + dotenv.get("TOKEN_SECRET_KEY"));
        // System.out.println("SMTP_HOST: " + dotenv.get("SMTP_HOST"));
        // System.out.println("SMTP_PORT: " + dotenv.get("SMTP_PORT"));
        // System.out.println("MAIL_USERNAME: " + dotenv.get("MAIL_USERNAME"));
        // System.out.println("MAIL_PASSWORD: " + dotenv.get("MAIL_PASSWORD"));
        // System.out.println("email-service.url: " + dotenv.get("email-service.url"));
    }

	public static void main(String[] args) {
			
		SpringApplication.run(KibernumApplication.class, args);
	}

}
