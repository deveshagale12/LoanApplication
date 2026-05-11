package com.LoanApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/emi")
	@CrossOrigin("*")

public class EMIController {

    @Autowired private EMIService emiService;

    @PostMapping("/calculate")
    public ResponseEntity<EMI> calculate(@RequestBody EMI request) {
        return ResponseEntity.ok(emiService.calculateAndSaveEMI(request));
    }

    @GetMapping("/{loanId}")
    public ResponseEntity<EMI> getDetails(@PathVariable Long loanId) {
        return ResponseEntity.ok(emiService.getEmiDetails(loanId));
    }
}