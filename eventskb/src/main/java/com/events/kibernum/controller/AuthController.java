package com.events.kibernum.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.events.kibernum.model.Users;
import com.events.kibernum.repository.UserRepository;
import com.events.kibernum.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;  // Asegúrate de tener esto

    @Autowired
    private JwtService jwtService;

    
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new Users());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") Users user) {
        if (user.getEmail() == null || user.getPassword() == null || user.getName() == null) {
            // Manejo del error, redirigir a una página de error o mostrar un mensaje
            
            return "error";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        userRepository.save(user);

        // si el registro sale bien, lo redirecciona a la pagina d elogin
        return "redirect:/login";
    }

    // RESTABLECER CONTRASEÑA
    @GetMapping("/resetpass")
    public String resetPass(Model model) {
        model.addAttribute("user", new Users());
        return "resetpass";
    }


    @PostMapping("/resetpass")
    @ResponseBody
    public Map<String, String> resetPass(@RequestParam("email") String email, HttpServletRequest request) {
        Users user = userRepository.findByEmail(email);
        Map<String, String> response = new HashMap<>();

        if (user == null) {
            response.put("message", "El usuario no existe");
            return response;
        }


        String token = jwtService.generateToken(user.getEmail());

        String resetUrl = request.getRequestURL().toString().replace(request.getServletPath(), "") + "/restorepass?token=" + token;


        // Enviar email con el link de restablecimiento
        String subject = "Restablecimiento de contraseña";
        String text = "Haz clic en el siguiente enlace para restablecer tu contraseña: " + resetUrl;
        
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);

        response.put("message", "Se ha enviado un enlace de restablecimiento a tu correo electrónico "+ email);
        return response;
    }

}
