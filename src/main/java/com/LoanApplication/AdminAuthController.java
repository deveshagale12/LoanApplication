package com.LoanApplication;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController // <--- CRITICAL: Must be @RestController, not @Controller
@RequestMapping("/api/admin/auth")
	@CrossOrigin("*")

public class AdminAuthController {

    @Autowired 
    private AdminAuthService adminAuthService;
    @PostMapping("/register")
    public ResponseEntity<Admin> register(@RequestBody Admin admin) {
        return ResponseEntity.ok(adminAuthService.registerAdmin(admin));
    }

    @PostMapping("/login")
    public ResponseEntity<Admin> login(@RequestBody Map<String, String> credentials) {
        Admin admin = adminAuthService.loginAdmin(credentials.get("email"), credentials.get("password"));
        return ResponseEntity.ok(admin);
    }
}