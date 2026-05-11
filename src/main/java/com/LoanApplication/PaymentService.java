package com.LoanApplication;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired private PaymentRepository paymentRepository;
    @Autowired private LoanRepository loanRepository;
    @Autowired private LoanTypeService emailService;
    @Autowired private UserRepository userRepository;

    public Payment processPayment(Payment payment) {
        // 1. Verify Loan exists AND fetch the loan details
        Loan loan = loanRepository.findById(payment.getLoanApplicationId())
                .orElseThrow(() -> new PaymentException("Loan not found."));

        // 2. Fetch the User associated with that specific loan
        User user = userRepository.findById(loan.getUserId())
                .orElseThrow(() -> new PaymentException("User associated with loan not found."));

        // 3. Process the payment logic
        payment.setPaymentDate(LocalDateTime.now());
        payment.setTransactionId("TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        payment.setPaymentStatus("SUCCESS");
        Payment savedPayment = paymentRepository.save(payment);

        // 4. Send email to the ACTUAL user's email
        String recipientEmail = user.getEmail(); // Dynamic Email
        
        String htmlContent = "<h2>Payment Received</h2>" +
                             "<p>Hello " + user.getFullName() + ",</p>" +
                             "<p>Your payment of ₹" + savedPayment.getAmount() + " is successful.</p>";

        // PASS recipientEmail HERE instead of "deveshagale@gmail.com"
        emailService.sendHtmlMail(recipientEmail, "Payment Confirmation - " + savedPayment.getTransactionId(), htmlContent);

        return savedPayment;
    }

    public List<Payment> getHistory(Long loanId) {
        List<Payment> history = paymentRepository.findHistoryByLoanIdNative(loanId);
        if (history.isEmpty()) {
            throw new PaymentException("No payment history found for Loan ID: " + loanId);
        }
        return history;
    }
}