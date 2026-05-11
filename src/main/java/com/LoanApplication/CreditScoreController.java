package com.LoanApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/credit")
	@CrossOrigin("*")

public class CreditScoreController {
    @Autowired private CreditScoreService creditService;

    @PostMapping("/check")
    public ResponseEntity<CreditScore> check(@RequestBody CreditScore request) {
        return ResponseEntity.ok(creditService.checkAndSaveScore(request));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CreditScore> getScore(@PathVariable Long userId) {
        return ResponseEntity.ok(creditService.getLatestScore(userId));
    }
}