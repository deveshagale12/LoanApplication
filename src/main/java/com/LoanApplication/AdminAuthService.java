
package com.LoanApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AdminAuthService {

    // IMPORTANT: Ensure this repository extends JpaRepository<Admin, Long>
    @Autowired 
    private AdminRepository adminRepository;

    @Autowired 
    private LoanTypeService emailService;

    /**
     * Registers a new Admin and sends a security alert to the master email.
     */
    public Admin registerAdmin(Admin admin) {
        // 1. Check if admin already exists using Native SQL
        Optional<Admin> existingAdmin = adminRepository.findByEmailNative(admin.getEmail());
        if (existingAdmin.isPresent()) {
            throw new AdminException("Registration Failed: Email " + admin.getEmail() + " is already registered.");
        }

        // 2. Save the Admin (This will no longer show an error)
        Admin savedAdmin = adminRepository.save(admin);

        // 3. Send Security Notification to your master admin email
        String subject = "Security Alert: New Admin Account Created";
        String htmlBody = "<div style='font-family: Arial; border: 1px solid #ff9800; padding: 15px;'>" +
                "<h2 style='color: #ff9800;'>New Admin Registered</h2>" +
                "<p>A new administrative account has been created in the system.</p>" +
                "<ul>" +
                "<li><b>Name:</b> " + savedAdmin.getName() + "</li>" +
                "<li><b>Email:</b> " + savedAdmin.getEmail() + "</li>" +
                "<li><b>Role:</b> " + savedAdmin.getRole() + "</li>" +
                "<li><b>Timestamp:</b> " + LocalDateTime.now() + "</li>" +
                "</ul>" +
                "<p>If you did not authorize this, please check your database immediately.</p>" +
                "</div>";

        emailService.sendHtmlMail("noreplyloanapp12@gmail.com", subject, htmlBody);

        return savedAdmin;
    }

    /**
     * Validates admin credentials for login.
     */
    public Admin loginAdmin(String email, String password) {
        // 1. Find Admin by email
        Admin admin = adminRepository.findByEmailNative(email)
                .orElseThrow(() -> new AdminException("Login Failed: Admin email not found."));

        // 2. Simple password check (In production, use BCrypt)
        if (!admin.getPassword().equals(password)) {
            throw new AdminException("Login Failed: Incorrect password.");
        }

        return admin;
    }
}