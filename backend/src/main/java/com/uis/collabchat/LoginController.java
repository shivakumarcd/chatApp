package com.uis.collabchat;

import com.uis.collabchat.dtos.LoginRequestDTO;
import com.uis.collabchat.dtos.RegistrationRequestDTO;
import com.uis.collabchat.util.JwtUtil;
import com.uis.collabchat.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.uis.collabchat.repository.UserRepository;
import com.uis.collabchat.entity.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class LoginController {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;
    // Hardcoded POC credentials (plaintext) - for POC only
    private static final String USERNAME = "test";
    private static final String PLAINTEXT_PASSWORD = "test123";

    @PostMapping(path = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO req) {
        log.info("Login attempt for user: {}", (req != null ? req.getUsername() : "null"));
        if (req == null || req.getUsername() == null || req.getPasswordHash() == null) {
            return ResponseEntity.badRequest().body("Missing username or passwordHash");
        }

        if (!USERNAME.equals(req.getUsername())) {
            log.warn("Invalid username: {}", req.getUsername());
            return ResponseEntity.status(401).body("Invalid credentials1");
        }

        String expectedHash = PasswordUtil.sha256Hex(PLAINTEXT_PASSWORD);
        if (!expectedHash.equals(req.getPasswordHash())) {
            log.warn("Invalid password hash for user: {}", req.getUsername());
            return ResponseEntity.status(401).body("Invalid credentials2");
        }

        log.info("User {} authenticated successfully. Generating token.", req.getUsername());
        String token = JwtUtil.createToken(USERNAME);
        return ResponseEntity.ok().body(java.util.Map.of("token", token));
    }

    @PostMapping("/registrationBCrypt")
    public ResponseEntity<?> register(@RequestBody RegistrationRequestDTO req) {
        log.info("RegistrationBcrypt attempt for user: {}", (req != null ? req.getUsername() : "null"));
        if (req == null) {
            return ResponseEntity.badRequest().body("Missing username or password");
        }

        if (userRepository.existsByUsername(req.getUsername())) {
            log.warn("Username already exists: {}", req.getUsername());
            return ResponseEntity.status(401).body("Username already exists");
        }

        String hashed = encoder.encode(req.getPassword());
        User user = new User(req.getUsername(), hashed);
        userRepository.save(user);

        log.info("User registered successfully: {}", req.getUsername());
        return ResponseEntity.ok().body("User registered successfully");
    }

    @PostMapping("/login2")
    public ResponseEntity<?> loginBCrypt(@RequestBody LoginRequestDTO req) {
        log.info("LoginBcrypt attempt for user: {}", (req != null ? req.getUsername() : "null"));
        User user = userRepository.findByUsername(req.getUsername());
        if (user == null) {
            log.warn("Missing username or password for user: {}", req.getUsername());
            return ResponseEntity.badRequest().body("Missing username or password");
        }

        if (!encoder.matches(req.getPassword(), user.getPassword())) {
            log.warn("Invalid credentials for user: {}", req.getUsername());
            return ResponseEntity.status(401).body("Invalid credentials2");
        }

        log.info("User {} authenticated successfully (BCrypt). Generating token.", user.getUsername());
        String token = JwtUtil.createToken(user.getUsername());
        return ResponseEntity.ok().body(java.util.Map.of("token", token));
    }
}