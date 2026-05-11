package com.LoanApplication;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class LoanExceptionHandler {

    @ExceptionHandler(LoanException.class)
    public ResponseEntity<Object> handleLoanException(LoanException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("type", "LOAN_ERROR");
        body.put("message", ex.getMessage());
        
        // You can add logic here to log specific loan failures 
        // useful for audit trials in financial apps.
        System.out.println("Loan Processing Failed: " + ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}