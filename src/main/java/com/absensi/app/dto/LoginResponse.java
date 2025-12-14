package com.absensi.app.dto;

public class LoginResponse {
    private boolean success;
    private String message;
    private String role;
    private Long userId;
    private String userNama;

    public LoginResponse(boolean success, String message, String role, Long userId, String userNama) {
        this.success = success;
        this.message = message;
        this.role = role;
        this.userId = userId;
        this.userNama = userNama;
    }

    // Getter & Setter
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getUserNama() { return userNama; }
    public void setUserNama(String userNama) { this.userNama = userNama; }
}
