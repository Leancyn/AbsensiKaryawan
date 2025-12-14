package com.absensi.app.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        String role = (String) session.getAttribute("userRole");
        if (!"USER".equals(role)) return "redirect:/"; // proteksi role

        model.addAttribute("userNama", session.getAttribute("userNama"));
        return "user_dashboard"; // template HTML karyawan
    }
}
