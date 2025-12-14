package com.absensi.app.controller;

import com.absensi.app.model.Absensi;
import com.absensi.app.repository.AbsensiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/absensi")
public class AbsensiController {

    @Autowired
    private AbsensiRepository absensiRepository;

    // Submit absensi
    @PostMapping("/checkin")
    public Absensi checkIn(@RequestParam String userId,
                            @RequestParam String userNama,
                            @RequestParam double latitude,
                            @RequestParam double longitude,
                            @RequestBody byte[] foto) {

        Absensi absensi = new Absensi(userId, userNama, latitude, longitude, LocalDateTime.now(), foto);
        return absensiRepository.save(absensi);
    }

    // Admin: list absensi pending
    @GetMapping("/pending")
    public List<Absensi> getPending() {
        return absensiRepository.findByStatus("pending");
    }

    // Admin approve/reject
    @PostMapping("/{id}/approve")
    public ResponseEntity<String> approve(@PathVariable Long id) {
        Absensi abs = absensiRepository.findById(id).orElse(null);
        if (abs == null) return ResponseEntity.notFound().build();
        abs.setStatus("approved");
        absensiRepository.save(abs);
        return ResponseEntity.ok("Approved");
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<String> reject(@PathVariable Long id) {
        Absensi abs = absensiRepository.findById(id).orElse(null);
        if (abs == null) return ResponseEntity.notFound().build();
        abs.setStatus("rejected");
        absensiRepository.save(abs);
        return ResponseEntity.ok("Rejected");
    }

    // Admin: list semua absensi (pending + approved + rejected)
    @GetMapping("/all")
    public List<Absensi> getAll() {
        return absensiRepository.findAll();
    }

}
