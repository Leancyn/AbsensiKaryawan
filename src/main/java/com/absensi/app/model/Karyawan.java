package com.absensi.app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Karyawan {

    @Id

    private Long id;

    private String nip;
    private String nama;
    private String password;
    private String role; // "ADMIN" atau "KARYAWAN"

    // ===== GETTER & SETTER =====

    public Long getId() {
        return id;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() 
    { 
        return role; 
    }

    public void setRole(String role) 
    { 
        this.role = role; 
    }
}
