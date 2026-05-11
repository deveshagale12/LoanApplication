package com.LoanApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class EMIService {

    @Autowired private EMIRepository emiRepository;
    @Autowired private LoanRepository loanRepository;
    @Autowired private LoanTypeService emailService; // Reusing your HTML email logic

    public EMI calculateAndSaveEMI(EMI request) {
        // 1. Verify Loan Application exists
        if (!loanRepository.existsById(request.getLoanAppId())) {
            throw new LoanException("EMI Error: Loan Application ID " + request.getLoanAppId() + " not found.");
        }

        // 2. EMI Formula: [P x R x (1+R)^N]/[(1+R)^N-1]
        double p = request.getPrincipalAmount();
        double r = (request.getInterestRate() / 12) / 100;
        int n = request.getTenureMonths();

        double emiValue = (p * r * Math.pow(1 + r, n)) / (Math.pow(1 + r, n) - 1);
        
        request.setMonthlyEmi(Math.round(emiValue * 100.0) / 100.0);
        request.setTotalPayable(Math.round((emiValue * n) * 100.0) / 100.0);
        request.setCalculationDate(LocalDateTime.now());

        EMI savedEmi = emiRepository.save(request);

        // 3. Send Styled HTML Mail
        String htmlContent = "<div style='font-family:sans-serif; border:1px solid #eee; padding:20px;'>" +
                "<h2 style='color:#2e7d32;'>EMI Amortization Details</h2>" +
                "<p>Loan ID: <b>" + savedEmi.getLoanAppId() + "</b></p>" +
                "<table border='1' cellpadding='10' style='border-collapse:collapse;'>" +
                "<tr><td>Monthly Installment (EMI)</td><td><b>₹" + savedEmi.getMonthlyEmi() + "</b></td></tr>" +
                "<tr><td>Total Interest + Principal</td><td>₹" + savedEmi.getTotalPayable() + "</td></tr>" +
                "<tr><td>Tenure</td><td>" + savedEmi.getTenureMonths() + " Months</td></tr>" +
                "</table></div>";
        
        emailService.sendHtmlMail("deveshagale@gmail.com", "Your Loan EMI Schedule", htmlContent);

        return savedEmi;
    }

    public EMI getEmiDetails(Long loanId) {
        return emiRepository.findByLoanAppIdNative(loanId)
                .orElseThrow(() -> new LoanException("No EMI records found for Loan ID: " + loanId));
    }
}