package com.LoanApplication;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
@Service
public class CreditScoreService {

    @Autowired private CreditScoreRepository creditRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private LoanTypeService emailService;

    public CreditScore checkAndSaveScore(CreditScore request) {
        // 1. Fetch the Particular User to get their Email
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new CreditException("User ID " + request.getUserId() + " not found."));

        // 2. Logic: Assign Remarks based on score
        int score = request.getScore();
        if (score < 300 || score > 900) throw new CreditException("Invalid Score: Must be between 300 and 900.");
        
        if (score >= 750) request.setRemarks("EXCELLENT");
        else if (score >= 650) request.setRemarks("GOOD");
        else if (score >= 550) request.setRemarks("FAIR");
        else request.setRemarks("POOR");

        request.setCheckedDate(LocalDateTime.now());
        CreditScore savedScore = creditRepository.save(request);

        // 3. Send Mail to the Particular User
        String htmlContent = "<div style='font-family: Arial; padding: 20px; border: 2px solid #3f51b5;'>" +
                "<h2>Credit Score Report</h2>" +
                "<p>Dear " + user.getFullName() + ",</p>" +
                "<p>Your credit score has been successfully checked.</p>" +
                "<h1 style='color: #3f51b5;'>" + savedScore.getScore() + "</h1>" +
                "<p>Rating: <b>" + savedScore.getRemarks() + "</b></p>" +
                "<p style='font-size: 12px;'>Checked on: " + savedScore.getCheckedDate() + "</p>" +
                "</div>";

        emailService.sendHtmlMail(user.getEmail(), "Your Credit Score Report", htmlContent);

        return savedScore;
    }

    public CreditScore getLatestScore(Long userId) {
        return creditRepository.findLatestByUserIdNative(userId)
                .orElseThrow(() -> new CreditException("No credit history found for User ID: " + userId));
    }
} 