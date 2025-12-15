package com.absensi.app.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.absensi.app.model.Absensi;
import com.absensi.app.repository.AbsensiRepository;

@RestController
@RequestMapping("/api/absensi")
public class AbsensiController {

    @Autowired
    private AbsensiRepository absensiRepository;

    // Submit absensi
    @PostMapping("/checkin")
    public Absensi checkIn(
        @RequestParam String userId,
        @RequestParam String userNama,
        @RequestParam double latitude,
        @RequestParam double longitude,
        @RequestParam("foto") MultipartFile foto) throws IOException {

        byte[] fotoBytes = foto.getBytes();

        Absensi absensi = new Absensi(
                userId,
                userNama,
                latitude,
                longitude,
                LocalDateTime.now(),
                fotoBytes
        );

        // Default status = pending
        absensi.setStatus("pending");

        return absensiRepository.save(absensi);
    }

    // Admin: list semua absensi
    @GetMapping("/all")
    public List<Map<String, Object>> getAll() {
        return absensiRepository.findAll().stream().map(a -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", a.getId());
            map.put("userNama", a.getUserNama());
            map.put("latitude", a.getLatitude());
            map.put("longitude", a.getLongitude());
            map.put("timestamp", a.getTimestamp());
            map.put("status", a.getStatus());
            if (a.getFoto() != null) {
                String base64 = Base64.getEncoder().encodeToString(a.getFoto());
                map.put("fotoBase64", "data:image/jpeg;base64," + base64);
            } else {
                map.put("fotoBase64", null);
            }
            return map;
        }).collect(Collectors.toList());
    }

    // Admin approve
    @PostMapping("/{id}/approve")
    public ResponseEntity<String> approve(@PathVariable Long id) {
        Absensi abs = absensiRepository.findById(id).orElse(null);
        if (abs == null) return ResponseEntity.notFound().build();
        abs.setStatus("approved");
        absensiRepository.save(abs);
        return ResponseEntity.ok("Approved");
    }

    // Admin reject
    @PostMapping("/{id}/reject")
    public ResponseEntity<String> reject(@PathVariable Long id) {
        Absensi abs = absensiRepository.findById(id).orElse(null);
        if (abs == null) return ResponseEntity.notFound().build();
        abs.setStatus("rejected");
        absensiRepository.save(abs);
        return ResponseEntity.ok("Rejected");
    }

    // Endpoint baru: riwayat absensi per user
    @GetMapping("/user/{userId}")
    public List<Map<String, Object>> getUserAbsensi(@PathVariable String userId) {
        return absensiRepository.findByUserId(userId).stream().map(a -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", a.getId());
            map.put("timestamp", a.getTimestamp());
            map.put("status", a.getStatus());
            if (a.getFoto() != null) {
                String base64 = Base64.getEncoder().encodeToString(a.getFoto());
                map.put("fotoBase64", "data:image/jpeg;base64," + base64);
            } else {
                map.put("fotoBase64", null);
            }
            return map;
        }).collect(Collectors.toList());
    }

}
