package com.absensi.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

    /* =========================
       DASHBOARD
    ========================== */
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/";

        model.addAttribute("userNama", session.getAttribute("userNama"));
        return "admin/dashboard";
    }

    /* =========================
       ABSENSI KARYAWAN
    ========================== */
    @GetMapping("/absensi")
    public String absensi(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/";

        model.addAttribute("userNama", session.getAttribute("userNama"));
        return "admin/absensi";
    }

    /* =========================
       CUTI KARYAWAN
    ========================== */
    @GetMapping("/cuti")
    public String cuti(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/";

        model.addAttribute("userNama", session.getAttribute("userNama"));
        return "admin/cuti";
    }

    /* =========================
       HELPER ROLE CHECK
    ========================== */
    private boolean isAdmin(HttpSession session) {
        String role = (String) session.getAttribute("userRole");
        return "ADMIN".equals(role);
    }
}
