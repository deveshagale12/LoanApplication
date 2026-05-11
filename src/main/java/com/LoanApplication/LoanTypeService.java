package com.LoanApplication;


import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LoanTypeService {

    @Autowired
    private LoanTypeRepository loanTypeRepository;

    @Autowired
    private JavaMailSender mailSender;

    public void addLoanType(LoanType loanType) {
        // 1. Validation Logic
        if (loanType.getMaxAmount() < loanType.getMinAmount()) {
            throw new LoanTypeException("Invalid Range: Max amount (" + loanType.getMaxAmount() + 
                                        ") cannot be less than Min amount (" + loanType.getMinAmount() + ").");
        }

        // 2. Call the Repository using Native SQL
        // This ensures high performance by bypassing the Hibernate persistence context
        loanTypeRepository.saveLoanTypeNative(
            loanType.getLoanName(),
            loanType.getMaxAmount(),
            loanType.getMinAmount(),
            loanType.getInterestRate(),
            loanType.getDescription(),
            loanType.getProcessingFee()
        );
        
        // 3. Admin Notification Logic
        // Using your specific admin email: noreplyloanapp12@gmail.com
        String adminEmail = "noreplyloanapp12@gmail.com";
        String subject = "System Alert: New Loan Product Registered";
        
        String htmlBody = "<div style='font-family: Arial; border: 1px solid #2196F3; padding: 15px;'>" +
                          "<h2 style='color: #2196F3;'>New Loan Product Added</h2>" +
                          "<p><b>Product Name:</b> " + loanType.getLoanName() + "</p>" +
                          "<p><b>Description:</b> " + loanType.getDescription() + "</p>" +
                          "<ul>" +
                          "<li><b>Interest Rate:</b> " + loanType.getInterestRate() + "%</li>" +
                          "<li><b>Amount Range:</b> ₹" + loanType.getMinAmount() + " - ₹" + loanType.getMaxAmount() + "</li>" +
                          "<li><b>Processing Fee:</b> ₹" + loanType.getProcessingFee() + "</li>" +
                          "</ul>" +
                          "<p style='font-size: 12px; color: #888;'>This is an automated system notification.</p>" +
                          "</div>";
        
        // 4. Send the HTML Mail
        sendHtmlMail(adminEmail, subject, htmlBody);
    }
    public List<LoanType> getAll() {
        List<LoanType> list = loanTypeRepository.findAllNative();
        sendHtmlMail("agaledeva4@gmail.com", "Audit Log", "<p>Loan types were viewed by an administrator.</p>");
        return list;
    }

    @Async
    public void sendHtmlMail(String to, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true); // Set true for HTML
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Email failed: " + e.getMessage());
        }
    }
    
    public void updateLoanType(Long id, LoanType lt) {
        if (!loanTypeRepository.existsById(id)) {
            throw new LoanTypeException("Update Failed: Loan Type ID " + id + " not found.");
        }

        loanTypeRepository.updateLoanTypeNative(
            id, lt.getLoanName(), lt.getMaxAmount(), lt.getMinAmount(), 
            lt.getInterestRate(), lt.getDescription(), lt.getProcessingFee()
        );

        String htmlBody = "<div style='font-family: Arial; border: 1px solid #ddd; padding: 20px;'>" +
                          "<h2 style='color: #2c3e50;'>Loan Product Updated</h2>" +
                          "<p>The details for <b>" + lt.getLoanName() + "</b> (ID: " + id + ") have been revised.</p>" +
                          "<p>New Interest Rate: <span style='color: green;'>" + lt.getInterestRate() + "%</span></p>" +
                          "</div>";

        sendHtmlMail("admin@loanapp.com", "Product Catalog Updated", htmlBody);
    }

    public void deleteLoanType(Long id) {
        if (!loanTypeRepository.existsById(id)) {
            throw new LoanTypeException("Delete Failed: Loan Type ID " + id + " does not exist.");
        }

        loanTypeRepository.deleteLoanTypeNative(id);
        
        sendHtmlMail("admin@loanapp.com", "Product Deleted", 
            "<h3>Warning</h3><p>Loan Type ID: " + id + " has been removed from the system catalog.</p>");
    }
}