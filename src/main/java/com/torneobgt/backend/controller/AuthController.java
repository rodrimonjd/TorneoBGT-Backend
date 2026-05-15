package com.torneobgt.backend.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.torneobgt.backend.dto.request.LoginRequest;
import com.torneobgt.backend.dto.request.RegisterRequest;
import com.torneobgt.backend.dto.response.LoginResponse;
import com.torneobgt.backend.model.User;
import com.torneobgt.backend.model.enums.Role;
import com.torneobgt.backend.service.JwtService;
import com.torneobgt.backend.service.UserService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
    	
        Optional<User> optUser = userService.findByEmail(request.getEmail());

        if (optUser.isEmpty()) {
            return ResponseEntity.status(401)
                    .body(Map.of("message", "Credenciales inválidas"));
        }

        User user = optUser.get();

        if (!userService.passwordMatches(request.getPassword(), user)) {
            return ResponseEntity.status(401)
                    .body(Map.of("message", "Credenciales inválidas"));
        }

        if (!user.getRole().name().equalsIgnoreCase(request.getRoleSolicited())) {
            return ResponseEntity.status(403)
                    .body(Map.of("message",
                        "No tienes permisos para ingresar como " + request.getRoleSolicited()));
        }

        String token = jwtService.generateToken(user.getEmail(), user.getRole().name());

        return ResponseEntity.ok(new LoginResponse(
                token,
                user.getRole().name(),
                user.getNombre(),
                user.getEmail(),
                "Login exitoso"
        ));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (userService.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "El email ya está registrado"));
        }

        User user = new User();
        user.setNombre(request.getNombre());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(Role.valueOf(request.getRole().toUpperCase()));

        User saved = userService.save(user);

        return ResponseEntity.ok(Map.of(
            "message", "Usuario creado",
            "id", saved.getId()
        ));
    }
}