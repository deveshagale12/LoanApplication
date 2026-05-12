package com.LoanApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.List;



import java.util.List;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired 
    private UserRepository userRepository;

    // 1. Apply for Loan
    public void applyLoan(Loan loan) {
        // 1. DATA INTEGRITY CHECK & USER RETRIEVAL
        // Instead of just checking if they exist, we fetch the User object 
        // so we can access their specific email address.
        User user = userRepository.findById(loan.getUserId())
                .orElseThrow(() -> new LoanException("Application Cancelled: User with ID " + loan.getUserId() + " does not exist."));

        // 2. BUSINESS RULE: Check monthly income
        if (loan.getMonthlyIncome() < 25000) {
            throw new LoanException("Application Rejected: Minimum monthly income of 25,000 required.");
        }

        // 3. PERSIST: Native SQL Insert
        loanRepository.applyLoanNative(loan);

        // 4. NOTIFY: Sent to the particular user's email
        // We use user.getEmail() which was fetched in step 1
        String recipientEmail = user.getEmail();
        String subject = "Loan Application Submitted - " + loan.getLoanType();
        String body = "Dear " + user.getFullName() + ",\n\n" +
                      "Your application for a " + loan.getLoanType() + " has been successfully submitted.\n" +
                      "Amount Requested: ₹" + loan.getLoanAmount() + "\n" +
                      "Our team will review your application shortly.";

        sendProperMail(recipientEmail, subject, body);
    }
    // 2. Get All Loans
    public List<Loan> getAllLoans() {
        return loanRepository.findAllLoansNative();
    }

    // 3. Get Loan Details by ID
    public Loan getLoanById(Long id) {
        return loanRepository.findById(id)
                .orElseThrow(() -> new LoanException("Loan record with ID " + id + " not found."));
    }

    // 4. Update Loan (Status, Amount, etc.)
    public void updateLoan(Long id, Loan loanDetails) {
        // 1. Fetch the existing loan to find the associated User ID
        Loan existingLoan = loanRepository.findById(id)
                .orElseThrow(() -> new LoanException("Update Failed: Loan ID " + id + " not found."));

        // 2. Fetch the actual user's email (Strictly no hardcoding)
        String userEmail = userRepository.findById(existingLoan.getUserId())
                .map(user -> user.getEmail())
                .orElseThrow(() -> new LoanException("Critical: No user found for this loan ID. Email cannot be sent."));

        // 3. Status Logic & Body Construction
        String subject = "Loan Status Update - ID: " + id;
        StringBuilder body = new StringBuilder();
        body.append("Dear Customer,\n\n");
        body.append("Your loan status has been updated to: ").append(loanDetails.getStatus());

        if ("APPROVED".equals(loanDetails.getStatus())) {
            body.append("\nCongratulations! Your loan has been approved.");
            // Automatically set approval date if the admin hasn't provided one
            if (loanDetails.getApprovedDate() == null) {
                loanDetails.setApprovedDate(java.time.LocalDateTime.now());
            }
        } else if ("REJECTED".equals(loanDetails.getStatus())) {
            // Provide feedback on why it was rejected
            String reason = (loanDetails.getRejectedReason() != null) ? loanDetails.getRejectedReason() : "Criteria not met.";
            body.append("\nReason for Rejection: ").append(reason);
        }

        body.append("\n\nRegards,\nBajaj Finserv Team");

        // 4. Persistence: Update Database via Native SQL
        // Ensure your repository method takes id, status, approvedDate, and rejectedReason
        loanRepository.updateLoanStatusNative(
            id, 
            loanDetails.getStatus(), 
            loanDetails.getApprovedDate(), 
            loanDetails.getRejectedReason()
        );

        // 5. Send Notification to the Particular User
        sendProperMail(userEmail, subject, body.toString());
    }

    // 5. Delete Loan
    public void deleteLoan(Long id) {
        // 1. Fetch the existing loan first to get the User ID
        Loan existingLoan = loanRepository.findById(id)
                .orElseThrow(() -> new LoanException("Delete Failed: Loan ID " + id + " does not exist."));

        // 2. Fetch the user's email associated with this loan
        String userEmail = userRepository.findById(existingLoan.getUserId())
                .map(User::getEmail)
                .orElseThrow(() -> new LoanException("Critical: User not found. Cannot send deletion alert."));

        // 3. Perform the Native SQL Delete
        loanRepository.deleteLoanNative(id);

        // 4. Send the notification to the PARTICULAR user
        String subject = "Loan Record Deleted - ID: " + id;
        String body = "Dear Customer,\n\n" +
                      "This is to inform you that your loan application record (ID: " + id + ") " +
                      "has been permanently removed from our system.\n\n" +
                      "If you did not request this, please contact support immediately.";

        sendProperMail(userEmail, subject, body);
    }

    // --- PROPER ASYNC MAIL METHOD ---
    @Async
    public void sendProperMail(String toEmail, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("noreplyloanapp12@gmail.com");
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(body);

            mailSender.send(message);
            System.out.println("Email successfully sent to " + toEmail + " on thread: " + Thread.currentThread().getName());
        } catch (Exception e) {
            // Log the error but don't stop the main transaction
            System.err.println("SMTP Error: Could not send email to " + toEmail + " - " + e.getMessage());
        }
    }
     public List<Loan> getLoansByUserId(Long userId) {
        return loanRepository.findByUserId(userId);
    }
}