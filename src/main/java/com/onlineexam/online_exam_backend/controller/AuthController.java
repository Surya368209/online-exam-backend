package com.onlineexam.online_exam_backend.controller;

import com.onlineexam.online_exam_backend.dto.AuthRequest;
import com.onlineexam.online_exam_backend.dto.AuthResponse;
import com.onlineexam.online_exam_backend.dto.ChangePasswordRequest;
import com.onlineexam.online_exam_backend.dto.RegisterRequest;
import com.onlineexam.online_exam_backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        return authService.registerUser(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return authService.login(request);
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestBody ChangePasswordRequest request) {
        return authService.changePassword(request);
    }
}
