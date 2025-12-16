package com.absensi.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.absensi.app.model.Cuti;
import com.absensi.app.repository.CutiRepository;

@RestController
@RequestMapping("/api/cuti")
public class CutiController {

    @Autowired
    private CutiRepository cutiRepository;

    // ===============================
    // USER AJUKAN CUTI
    // ===============================
    @PostMapping("/ajukan")
    public ResponseEntity<?> ajukan(@RequestBody Cuti cuti) {

        if (cuti.getUserId() == null || cuti.getUserId().isEmpty()) {
            return ResponseEntity.badRequest().body("userId wajib diisi");
        }

        cuti.setStatus("pending");
        return ResponseEntity.ok(cutiRepository.save(cuti));
    }

    // ===============================
    // RIWAYAT CUTI USER  ✅ INI YANG HILANG
    // ===============================
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Cuti>> getByUser(
            @PathVariable String userId) {

        List<Cuti> data = cutiRepository.findByUserId(userId);
        return ResponseEntity.ok(data);
    }

    // ===============================
    // ADMIN - CUTI PENDING
    // ===============================
    @GetMapping("/pending")
    public List<Cuti> pending() {
        return cutiRepository.findByStatus("pending");
    }

    // ===============================
    // ADMIN APPROVE
    // ===============================
    @PostMapping("/{id}/approve")
    public ResponseEntity<?> approve(@PathVariable Long id) {
        return cutiRepository.findById(id)
                .map(c -> {
                    c.setStatus("approved");
                    cutiRepository.save(c);
                    return ResponseEntity.ok("Approved");
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // ===============================
    // ADMIN REJECT
    // ===============================
    @PostMapping("/{id}/reject")
    public ResponseEntity<?> reject(@PathVariable Long id) {
        return cutiRepository.findById(id)
                .map(c -> {
                    c.setStatus("rejected");
                    cutiRepository.save(c);
                    return ResponseEntity.ok("Rejected");
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
