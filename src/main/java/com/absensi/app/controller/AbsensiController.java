package com.absensi.app.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import javax.imageio.ImageIO;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.absensi.app.model.Absensi;
import com.absensi.app.repository.AbsensiRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;


@RestController
@RequestMapping("/api/absensi")
public class AbsensiController {

    private final AbsensiRepository absensiRepository;

    public AbsensiController(AbsensiRepository absensiRepository) {
        this.absensiRepository = absensiRepository;
    }

    // ================== ABSENSI ==================

    @PostMapping
    public Absensi simpan(
            @RequestParam String nip,
            @RequestParam String nama
    ) {
        Absensi a = new Absensi();
        a.setNip(nip);
        a.setNama(nama);
        a.setWaktu(LocalDateTime.now());
        return absensiRepository.save(a);
    }

    @GetMapping("/masuk")
    public ResponseEntity<String> masuk(
        @RequestParam String token,
        HttpServletRequest request
    ) {

    Absensi a = absensiRepository.findByToken(token)
            .orElse(null);

    if (a == null) {
        return ResponseEntity.badRequest().body("QR tidak valid");
    }

    if (a.isUsed()) {
        return ResponseEntity.badRequest().body("QR sudah digunakan");
    }

    if (a.getExpiredAt().isBefore(LocalDateTime.now())) {
        return ResponseEntity.badRequest().body("QR sudah kadaluarsa");
    }

    String ip = request.getRemoteAddr();

    // CONTOH: hanya izinkan IP lokal kantor
    if (!ip.startsWith("192.168.")) {
        return ResponseEntity
                .status(403)
                .body("Absensi harus dari jaringan kantor");
    }

    a.setIpAddress(ip);
    a.setWaktu(LocalDateTime.now());
    a.setUsed(true);
    absensiRepository.save(a);

    return ResponseEntity.ok("Absensi berhasil dari IP: " + ip);
    }




    // ================== QR CODE ==================

    @GetMapping(value = "/qr", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> qr(
            @RequestParam String nip,
            @RequestParam String nama
    ) throws Exception {

        String url = "http://localhost:8080/api/absensi/masuk?nip="
                + nip + "&nama=" + nama;

        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix matrix = writer.encode(url, BarcodeFormat.QR_CODE, 250, 250);

        BufferedImage image = new BufferedImage(250, 250, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < 250; x++) {
            for (int y = 0; y < 250; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? 0x000000 : 0xFFFFFF);
            }
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(image, "PNG", out);

        return ResponseEntity.ok(out.toByteArray());
    }

        // === ENDPOINT ADMIN REKAP ===
    @GetMapping("/admin/rekap")
    public List<Absensi> rekap(HttpSession session) {
        String role = (String) session.getAttribute("userRole");
        if (role == null || !role.equals("ADMIN")) {
            throw new RuntimeException("Akses ditolak: Hanya admin");
        }
        return absensiRepository.findAll();
    }
}


