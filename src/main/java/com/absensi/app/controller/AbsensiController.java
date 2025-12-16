package com.absensi.app.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.absensi.app.model.Absensi;
import com.absensi.app.repository.AbsensiRepository;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/absensi")
public class AbsensiController {

    @Autowired
    private AbsensiRepository absensiRepository;   // radius absensi

    /* =========================
       UTIL WAKTU HARI INI
    ========================== */
    private LocalDateTime startOfToday() {
        return LocalDateTime.now().toLocalDate().atStartOfDay();
    }

    private LocalDateTime endOfToday() {
        return startOfToday().plusDays(1).minusSeconds(1);
    }


    /* =========================
       USER CHECK-IN ABSENSI (GPS NYATA)
    ========================== */
    @PostMapping("/checkin")
    public ResponseEntity<?> checkIn(
            HttpSession session,
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam("foto") MultipartFile foto) throws IOException {

        Long userId = (Long) session.getAttribute("userId");
        String userNama = (String) session.getAttribute("userNama");

        if (userId == null) {
            return ResponseEntity.status(401).body("unauthorized");
        }

        Absensi absensi = new Absensi(
                userId.toString(),
                userNama,
                latitude,
                longitude,
                LocalDateTime.now(),
                foto.getBytes()
        );

        absensi.setStatus("pending");
        absensiRepository.save(absensi);

        return ResponseEntity.ok("ok");
    }


    /* =========================
       STATUS ABSENSI HARI INI
    ========================== */
    @GetMapping("/status-hari-ini")
    public ResponseEntity<String> statusHariIni(HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(401).body("unauthorized");
        }

        // Ambil semua absensi hari ini
        List<Absensi> list = absensiRepository
                .findByUserIdAndTimestampBetween(
                        userId.toString(),
                        startOfToday(),
                        endOfToday()
                );

        if (list.isEmpty()) {
            return ResponseEntity.ok("belum"); // belum absen
        }

        // Ambil absensi terakhir hari ini berdasarkan timestamp
        Absensi lastAbsensi = list.stream()
                .max((a, b) -> a.getTimestamp().compareTo(b.getTimestamp()))
                .get();

        return ResponseEntity.ok(lastAbsensi.getStatus());
    }


    /* =========================
       RIWAYAT ABSENSI USER
    ========================== */
    @GetMapping("/riwayat")
    public List<Map<String, Object>> riwayatUser(HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return List.of();
        }

        return absensiRepository.findByUserId(userId.toString())
                .stream()
                .map(a -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", a.getId());
                    map.put("timestamp", a.getTimestamp());
                    map.put("status", a.getStatus());

                    if (a.getFoto() != null) {
                        String base64 = Base64.getEncoder()
                                .encodeToString(a.getFoto());
                        map.put("fotoBase64", "data:image/jpeg;base64," + base64);
                    }

                    return map;
                })
                .collect(Collectors.toList());
    }

    /* =========================
       ADMIN: LIST SEMUA ABSENSI
    ========================== */
    @GetMapping("/all")
    public List<Map<String, Object>> getAll() {

        return absensiRepository.findAll()
                .stream()
                .map(a -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", a.getId());
                    map.put("userNama", a.getUserNama());
                    map.put("timestamp", a.getTimestamp());
                    map.put("status", a.getStatus());
                     // Tambahkan koordinat
                    map.put("latitude", a.getLatitude());
                    map.put("longitude", a.getLongitude());

                    if (a.getFoto() != null) {
                        String base64 = Base64.getEncoder()
                                .encodeToString(a.getFoto());
                        map.put("fotoBase64", "data:image/jpeg;base64," + base64);
                    }

                    return map;
                })
                .collect(Collectors.toList());
    }

    /* =========================
       ADMIN APPROVE / REJECT
    ========================== */
    @PostMapping("/{id}/approve")
    public ResponseEntity<String> approve(@PathVariable Long id) {
        return updateStatus(id, "approved");
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<String> reject(@PathVariable Long id) {
        return updateStatus(id, "rejected");
    }

    private ResponseEntity<String> updateStatus(Long id, String status) {
        return absensiRepository.findById(id)
                .map(a -> {
                    a.setStatus(status);
                    absensiRepository.save(a);
                    return ResponseEntity.ok(status);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
