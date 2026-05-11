package com.LoanApplication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/loan-types")
	@CrossOrigin("*")

public class LoanTypeController {
    @Autowired private LoanTypeService service;

    @PostMapping
    public ResponseEntity<Map<String, String>> add(@RequestBody LoanType loanType) {
        service.addLoanType(loanType);
        
        // Create a Map for the JSON response
        Map<String, String> response = new HashMap<>();
        response.put("message", "Loan Type Created Successfully");
        response.put("loanName", loanType.getLoanName());
        response.put("status", "NOTIFIED");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<LoanType>> getAll() {
        List<LoanType> list = service.getAll();
        // Returning the List directly will show a JSON Array [{}, {}]
        return ResponseEntity.ok(list);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> update(@PathVariable Long id, @RequestBody LoanType loanType) {
        service.updateLoanType(id, loanType);
        Map<String, String> response = new HashMap<>();
        response.put("status", "Success");
        response.put("message", "Loan Type ID " + id + " updated and email notification sent.");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        service.deleteLoanType(id);
        Map<String, String> response = new HashMap<>();
        response.put("status", "Deleted");
        response.put("message", "Loan Type ID " + id + " has been permanently removed.");
        return ResponseEntity.ok(response);
    }
}