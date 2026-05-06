package com.torneobgt.backend.dto.response;

public class LoginResponse {

    private String token;
    private String role;
    private String nombre;
    private String message;

    public LoginResponse(String token, String role, String nombre, String message) {
        this.token = token;
        this.role = role;
        this.nombre = nombre;
        this.message = message;
    }

    // Getters
    public String getToken() { return token; }
    public String getRole() { return role; }
    public String getNombre() { return nombre; }
    public String getMessage() { return message; }

    // Setters
    public void setToken(String token) { this.token = token; }
    public void setRole(String role) { this.role = role; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setMessage(String message) { this.message = message; }
}