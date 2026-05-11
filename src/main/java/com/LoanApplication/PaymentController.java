package com.LoanApplication;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/payments")
	@CrossOrigin("*")

public class PaymentController {
    @Autowired private PaymentService paymentService;

    @PostMapping("/pay")
    public ResponseEntity<Payment> pay(@RequestBody Payment payment) {
        return ResponseEntity.ok(paymentService.processPayment(payment));
    }

    @GetMapping("/{loanId}")
    public ResponseEntity<List<Payment>> getHistory(@PathVariable Long loanId) {
        return ResponseEntity.ok(paymentService.getHistory(loanId));
    }
}
