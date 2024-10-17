package com.events.kibernum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.events.kibernum.model.Users;
import com.events.kibernum.repository.UserRepository;
import com.events.kibernum.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;

@Controller
public class PasswordResetController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/restorepass")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
        try {
            // Validar el token JWT
            Claims claims = jwtService.validateToken(token);
            String userEmail = claims.getSubject();

            // Si el token es válido, mostramos el formulario para la nueva contraseña
            model.addAttribute("token", token);
            model.addAttribute("email", userEmail);
            return "resetPasswordForm";  // Redirige a una vista con el formulario de nueva contraseña

        } catch (ExpiredJwtException e) {
            model.addAttribute("error", "El token ha expirado.");
            return "error";  // Redirige a una vista de error

        } catch (SignatureException e) {
            model.addAttribute("error", "Token no válido.");
            return "error";  // Redirige a una vista de error

        } catch (Exception e) {
            model.addAttribute("error", "Ocurrió un error al procesar tu solicitud.");
            return "error";  // Redirige a una vista de error
        }
    }

    @PostMapping("/restorepass")
    public String resetPassword(@RequestParam("token") String token,
                                @RequestParam("password") String newPassword,
                                Model model) {
        try {
            // Validar el token JWT nuevamente antes de procesar el cambio de contraseña
            Claims claims = jwtService.validateToken(token);
            String userEmail = claims.getSubject();

            // Encontrar al usuario en la base de datos
            Users user = userRepository.findByEmail(userEmail);
            if (user == null) {
                model.addAttribute("error", "Usuario no encontrado.");
                return "error";  // Redirige a una vista de error
            }

            // Actualizar la contraseña del usuario
            // user.setPassword(newPassword);  // Asegúrate de codificar la contraseña antes de guardarla
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);

            // Redirigir a una página de confirmación o de inicio de sesión
            return "redirect:/login?passwordResetSuccess";

        } catch (ExpiredJwtException e) {
            model.addAttribute("error", "El token ha expirado.");
            return "error";  // Redirige a una vista de error

        } catch (SignatureException e) {
            model.addAttribute("error", "Token no válido.");
            return "error";  // Redirige a una vista de error

        } catch (Exception e) {
            model.addAttribute("error", "Ocurrió un error al procesar tu solicitud.");
            return "error";  // Redirige a una vista de error
        }
    }
}
