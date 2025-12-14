package com.absensi.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.absensi.app.repository.AbsensiRepository;

@Controller
public class HomeController {

    private final AbsensiRepository absensiRepository;

    public HomeController(AbsensiRepository absensiRepository) {
        this.absensiRepository = absensiRepository;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }
}
