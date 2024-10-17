package com.events.kibernum.controller;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.events.kibernum.model.Users;
import com.events.kibernum.repository.UserRepository;
import com.events.kibernum.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;

public class AuthControllerIntegrationTest {
  
    @InjectMocks
    private AuthController authController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private JwtService jwtService;

    @Mock
    private HttpServletRequest request;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterUser_Success() {
        Users user = new Users();
        user.setEmail("test@example.com");
        user.setName("Test User");
        user.setPassword("password");

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(Users.class))).thenReturn(user);

        String result = authController.registerUser(user);

        assertEquals("redirect:/login", result);
        verify(userRepository, times(1)).save(any(Users.class));
    }

    @Test
    public void testResetPass_Success() {
        
        // Crear un usuario de prueba
        Users user = new Users();
        user.setEmail("perico@example.com");
        user.setName("perico");
        user.setLast_name("los palotes");

        // Mockear el repositorio de usuario y el servicio JWT
        when(userRepository.findByEmail("perico@example.com")).thenReturn(user);
        when(jwtService.generateToken("perico@example.com")).thenReturn("mockToken");

        // Mockear el HttpServletRequest
        when(request.getRequestURL()).thenReturn(new StringBuffer("http://localhost/resetpass"));
        when(request.getServletPath()).thenReturn("/resetpass");

        // Capturar el correo enviado
        ArgumentCaptor<SimpleMailMessage> emailCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        // Llamar al método de restablecimiento de contraseña
        Map<String, String> response = authController.resetPass("perico@example.com", request);

        // Verificar el resultado
        assertEquals("Se ha enviado un enlace de restablecimiento a tu correo electrónico perico@example.com", response.get("message"));
        
        // Verificar que se llamó al método para enviar el correo
        verify(mailSender, times(1)).send(emailCaptor.capture());
        
        // Verificar el contenido del correo
        SimpleMailMessage sentEmail = emailCaptor.getValue();
        assertEquals("perico@example.com", sentEmail.getTo()[0]);
        assertEquals("Restablecimiento de contraseña", sentEmail.getSubject());
        assertTrue(sentEmail.getText().contains("http://localhost/restorepass?token=mockToken"));

        // Verificar que se llamaron a los métodos esperados
        verify(userRepository, times(1)).findByEmail("perico@example.com");
        verify(jwtService, times(1)).generateToken("perico@example.com");
    }
 
    @Test
    public void testResetPass_UserNotFound() {
        when(userRepository.findByEmail(anyString())).thenReturn(null);

        Map<String, String> response = authController.resetPass("nonexistent@example.com", request);

        assertEquals("El usuario no existe", response.get("message"));
        verify(mailSender, times(0)).send(any(SimpleMailMessage.class));
    }
}
