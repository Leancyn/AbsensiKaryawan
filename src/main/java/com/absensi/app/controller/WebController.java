package com.absensi.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/absensi")
    public String absensiPage() {
        return "absensi"; // merujuk ke absensi.html
    }
}
