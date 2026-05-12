package com.LoanApplication;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/loans")
	@CrossOrigin("*")

public class LoanController {

    @Autowired
    private LoanService loanService;

    // 1. Apply for a Loan (POST)
    @PostMapping("/apply")
    public ResponseEntity<Map<String, String>> applyLoan(@RequestBody Loan loan) {
        loanService.applyLoan(loan);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Loan application submitted successfully. Confirmation email sent.");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 2. Get All Loans (GET)
    @GetMapping
    public ResponseEntity<List<Loan>> getAllLoans() {
        List<Loan> loans = loanService.getAllLoans();
        return ResponseEntity.ok(loans);
    }

    // 3. Get Loan Details by ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<Loan> getLoanDetails(@PathVariable Long id) {
        Loan loan = loanService.getLoanById(id);
        return ResponseEntity.ok(loan);
    }

    // 4. Update Loan Status or Details (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateLoan(@PathVariable Long id, @RequestBody Loan loan) {
        loanService.updateLoan(id, loan);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Loan record updated successfully. Status email triggered.");
        return ResponseEntity.ok(response);
    }

    // 5. Delete Loan Record (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteLoan(@PathVariable Long id) {
        loanService.deleteLoan(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Loan record deleted successfully.");
        return ResponseEntity.ok(response);
    }

   @GetMapping("/users/{userId}")
public ResponseEntity<List<Loan>> getLoansByUserId(@PathVariable Long userId) {
    List<Loan> loans = loanService.getLoansByUserId(userId);
    return ResponseEntity.ok(loans);
}

}