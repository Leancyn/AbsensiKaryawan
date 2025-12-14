package com.absensi.app.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    
    @GetMapping("/admin/dashboard")
    public String dashboard(HttpSession session, Model model) {
        String role = (String) session.getAttribute("userRole");
        if (!"ADMIN".equals(role)) return "redirect:/"; // proteksi role

        model.addAttribute("userNama", session.getAttribute("userNama"));
        return "admin_dashboard"; // template HTML admin
    }
}
