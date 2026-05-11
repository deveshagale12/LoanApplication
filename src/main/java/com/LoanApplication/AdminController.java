package com.LoanApplication;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
	@CrossOrigin("*")

public class AdminController {

    @Autowired private AdminService adminService;
    @Autowired private AdminRepository adminRepo;
    @Autowired private LoanTypeService emailService;
    @Autowired private AdminAuthService adminAuthService;
   

    @GetMapping("/all-applications")
    public ResponseEntity<List<Loan>> viewAll() {
        return ResponseEntity.ok(adminService.getAllApplications());
    }

    @PutMapping("/approve/{loanId}")
    public ResponseEntity<Map<String, String>> approve(@PathVariable Long loanId) {
        String message = adminService.processLoan(loanId, "APPROVED");
        return ResponseEntity.ok(Collections.singletonMap("response", message));
    }

    @PutMapping("/reject/{loanId}")
    public ResponseEntity<Map<String, String>> reject(@PathVariable Long loanId) {
        String message = adminService.processLoan(loanId, "REJECTED");
        return ResponseEntity.ok(Collections.singletonMap("response", message));
    }
    
    
    @PostMapping("/process")
    public ResponseEntity<Map<String, String>> processLoan(
            @RequestParam Long loanId, 
            @RequestParam String status) {
            
        String message = adminService.processLoan(loanId, status);
        return ResponseEntity.ok(Collections.singletonMap("response", message));
    }

    
    
}