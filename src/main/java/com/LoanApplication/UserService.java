package com.LoanApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


import java.util.List;
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void register(User user) {
        // Check for unique email
        if (userRepository.existsByEmailNative(user.getEmail()) > 0) {
            throw new UserException("Registration failed: Email '" + user.getEmail() + "' is already in use.");
        }
        
        // Save using the updated Native SQL query
        userRepository.registerUser(user);
        
        // Trigger multi-threaded background process
        sendWelcomeEmail(user.getEmail());
    }

    @Async
    public void sendWelcomeEmail(String email) {
        try {
            // Simulate slow email server (3 seconds)
            Thread.sleep(3000);
            System.out.println("SUCCESS: Welcome email sent to " + email + " on thread: " + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public User login(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(u -> u.getPassword().equals(password))
                .orElseThrow(() -> new UserException("Invalid email or password."));
    }

    public User getProfile(Long id) {
        return userRepository.findByIdNative(id)
                .orElseThrow(() -> new UserException("User not found with ID: " + id));
    }

    public void updateProfile(Long id, User user) {
        // Ensure user exists before updating
        if (userRepository.findByIdNative(id).isEmpty()) {
            throw new UserException("Update failed: User ID " + id + " does not exist.");
        }
        userRepository.updateUser(id, user);
    }

    public void deleteUser(Long id) {
        if (userRepository.findByIdNative(id).isEmpty()) {
            throw new UserException("Delete failed: User ID " + id + " does not exist.");
        }
        userRepository.deleteUserNative(id);
    }
     public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}