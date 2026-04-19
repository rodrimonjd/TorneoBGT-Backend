package com.torneobgt.backend.model;

public class LoginRequest {

    private String email;
    private String password;
    private String roleSolicited;

    // Getters
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getRoleSolicited() { return roleSolicited; }

    // Setters
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setRoleSolicited(String roleSolicited) { this.roleSolicited = roleSolicited; }
}