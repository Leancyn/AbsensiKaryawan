package com.absensi.app.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "absensi")
public class Absensi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private String userNama;
    private double latitude;
    private double longitude;
    private LocalDateTime timestamp;

    @Lob
    private byte[] foto; // menyimpan foto dari kamera

    private String status; // "pending", "approved", "rejected"

    public Absensi() {}

    public Absensi(String userId, String userNama, double latitude, double longitude, LocalDateTime timestamp, byte[] foto) {
        this.userId = userId;
        this.userNama = userNama;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
        this.foto = foto;
        this.status = "pending";
    }

    // Getter & Setter
    public Long getId() { return id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUserNama() { return userNama; }
    public void setUserNama(String userNama) { this.userNama = userNama; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public byte[] getFoto() { return foto; }
    public void setFoto(byte[] foto) { this.foto = foto; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
