package com.onlineexam.online_exam_backend.dto;

public class AuthResponse {
    private String message;
    private String role;
    private boolean forceChangePassword;  // <-- Added this
    private String token;

    public AuthResponse(String message, String role, boolean forceChangePassword,String token) {
        this.message = message;
        this.role = role;
        this.forceChangePassword = forceChangePassword;
        this.token = token;
    }

    // Getters & Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean getForceChangePassword() {
        return forceChangePassword;
    }

    public void setForceChangePassword(boolean forceChangePassword) {
        this.forceChangePassword = forceChangePassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
