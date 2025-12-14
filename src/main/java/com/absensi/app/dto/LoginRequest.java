package com.absensi.app.dto;

public class LoginRequest {

    private String nip;
    private String password;

    // wajib: getter & setter
    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
