package com.LoanApplication;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class AdminService {

    @Autowired private AdminRepository adminRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private LoanTypeService emailService;
   @Autowired private LoanAdminRepository loanadminRepo;

   
    public List<Loan> getAllApplications() {
        return loanadminRepo.findAllApplicationsNative();
    }

    public String processLoan(Long loanId, String status) {
        // 1. Fetch Loan to find the User
        Loan loan = loanadminRepo.findById(loanId)
                .orElseThrow(() -> new AdminException("Loan ID " + loanId + " not found."));

        // 2. Fetch the Particular User who applied
        User user = userRepository.findById(loan.getUserId())
                .orElseThrow(() -> new AdminException("User associated with this loan no longer exists."));

        // 3. Update Status in DB
        loanadminRepo.updateLoanStatusNative(loanId, status);

        // 4. Send Mail to the Particular User
        String color = status.equals("APPROVED") ? "#2e7d32" : "#c62828";
        String htmlBody = "<div style='font-family: Arial; border: 1px solid "+color+"; padding: 20px;'>" +
                "<h2 style='color: "+color+";'>Loan Status: " + status + "</h2>" +
                "<p>Dear " + user.getFullName() + ",</p>" +
                "<p>Your loan application with <b>ID: " + loanId + "</b> has been " + status.toLowerCase() + " by our admin team.</p>" +
                "<p>Please log in to your dashboard for further instructions.</p>" +
                "</div>";

        emailService.sendHtmlMail(user.getEmail(), "Update on Loan Application #" + loanId, htmlBody);

        return "Loan " + status + " successfully. Notification sent to " + user.getEmail();
    }
}