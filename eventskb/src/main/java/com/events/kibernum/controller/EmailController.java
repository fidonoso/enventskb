package com.events.kibernum.controller;

import org.springframework.beans.factory.annotation.Autowired;  // Importar el DTO
import org.springframework.beans.factory.annotation.Value;  // Para inyección de dependencias
import org.springframework.http.HttpStatus;  // Para leer propiedades
import org.springframework.http.ResponseEntity;  // Para manejar los estados de respuesta
import org.springframework.web.bind.annotation.CrossOrigin;  // Para respuestas HTTP
import org.springframework.web.bind.annotation.PostMapping;  // Para el mapeo de solicitudes POST
import org.springframework.web.bind.annotation.RequestBody;  // Para manejar el cuerpo de la solicitud
import org.springframework.web.bind.annotation.RequestMapping;  // Para el mapeo de la URL
import org.springframework.web.bind.annotation.RestController;  // Para declarar la clase como controlador REST
import org.springframework.web.client.RestTemplate;  // Para hacer llamadas HTTP

import com.events.kibernum.dto.EmailRequest;  // Para manejar CORS (opcional)



@RestController
@RequestMapping("/api/emails")
@CrossOrigin(origins = "*")
public class EmailController {

    @Autowired
    private RestTemplate restTemplate;  // Inyección de RestTemplate

    @Value("${email-service.url}/send-email")  // URL de la API en Node.js
    private String emailServiceUrl;

    @Value("${email-service.url}/request-practicing")  // URL de la API en Node.js
    private String emailService;

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest emailRequest) {
        // Enviar solicitud POST a la API de Node.js
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(
                emailServiceUrl, emailRequest, String.class);

            return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al enviar correo", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/request-practicing")
    public ResponseEntity<String> sendEmailRequestPracticing(@RequestBody EmailRequest emailRequest) {
        // Enviar solicitud POST a la API de Node.js
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(
                emailService, emailRequest, String.class);

            return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al enviar correo", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
