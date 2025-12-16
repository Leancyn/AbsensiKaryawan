package com.absensi.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    private boolean isUser(HttpSession session) {
        return "USER".equals(session.getAttribute("userRole"));
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (!isUser(session)) return "redirect:/";
        model.addAttribute("userNama", session.getAttribute("userNama"));
        return "user/dashboard";
    }

    @GetMapping("/absensi")
    public String absensi(HttpSession session) {
        if (!isUser(session)) return "redirect:/";
        return "user/absensi";
    }

    @GetMapping("/cuti")
    public String cuti(HttpSession session) {
        if (!isUser(session)) return "redirect:/";
        return "user/cuti";
    }

    @GetMapping("/profile")
    public String profile(HttpSession session) {
        if (!isUser(session)) return "redirect:/";
        return "user/profile";
    }
    
}
