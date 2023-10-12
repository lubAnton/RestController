package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {


    @GetMapping
    public String adminPage() {
        return "admin_Page";
    }
}
