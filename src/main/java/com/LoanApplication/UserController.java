package com.LoanApplication;


import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    // POST /api/auth/register
    @PostMapping("/auth/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody User user) {
        userService.register(user);
        Map<String, String> response = new HashMap<>();
        response.put("message", "User registered successfully! Check your email shortly.");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // POST /api/auth/login
    @PostMapping("/auth/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        User authenticatedUser = userService.login(user.getEmail(), user.getPassword());
        return ResponseEntity.ok(authenticatedUser);
    }

    // GET /api/users/{id}
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getProfile(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getProfile(id));
    }

    // PUT /api/users/{id}
    @PutMapping("/users/{id}")
    public ResponseEntity<Map<String, String>> updateProfile(@PathVariable Long id, @RequestBody User user) {
        userService.updateProfile(id, user);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Profile updated successfully.");
        return ResponseEntity.ok(response);
    }

    // DELETE /api/users/{id}
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "User account deleted successfully.");
        return ResponseEntity.ok(response);
    }
     // GET /api/users
	    @GetMapping("/users")
	    public ResponseEntity<List<User>> getAllUsers() {
	        List<User> users = userService.getAllUsers();
	        return ResponseEntity.ok(users);
	    }
}