package com.absensi.app.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.absensi.app.dto.LoginRequest;
import com.absensi.app.dto.LoginResponse;
import com.absensi.app.model.Karyawan;
import com.absensi.app.repository.KaryawanRepository;

import jakarta.servlet.http.HttpSession;

@RestController
public class AuthController {

    private final KaryawanRepository karyawanRepository;

    public AuthController(KaryawanRepository karyawanRepository) {
        this.karyawanRepository = karyawanRepository;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest req, HttpSession session) {

        Karyawan k = karyawanRepository.findByNip(req.getNip());
        if (k == null) 
            return new LoginResponse(false, "NIP tidak terdaftar", null, null, null);

        if (!k.getPassword().equals(req.getPassword()))
            return new LoginResponse(false, "Password salah", null, null, null);

        // Simpan data login ke session
        session.setAttribute("userId", k.getId());
        session.setAttribute("userRole", k.getRole());
        session.setAttribute("userNama", k.getNama());
        session.setAttribute("nip", k.getNip());


        return new LoginResponse(true, "Login berhasil", k.getRole(), k.getId(), k.getNama());
    }

    @PostMapping("/logout")
    public LoginResponse logout(HttpSession session) {
        session.invalidate();
        return new LoginResponse(true, "Logout berhasil", null, null, null);
    }
}
