package com.example.TestPFA.controller;


import com.example.TestPFA.DTO.AuthRequest;
import com.example.TestPFA.DTO.RegisterRequest;
import com.example.TestPFA.JwtUtil;
import com.example.TestPFA.entity.Role;
import com.example.TestPFA.entity.User;
import com.example.TestPFA.repository.RoleRepository;
import com.example.TestPFA.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            // 1) Find user in DB to retrieve the role
            var userOpt = userRepository.findByUsername(request.getUsername());
            if (userOpt.isEmpty()) {
                return ResponseEntity.status(401).body("User not found");
            }
            var user = userOpt.get();

            // e.g. user.getRole().getName() -> "ROLE_ADMIN" or "ROLE_USER" or "ROLE_RECRUITER"
            String roleName = user.getRole().getName();

            // 2) Pass the role to the new generateToken(...) method
            String token = jwtUtil.generateToken(request.getUsername(), roleName);

            // 3) Send token back
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setPhoneNumber(registerRequest.getPhoneNumber());

        // Fetch the role based on the provided role name (default to "USER" if not specified)
        String roleName = registerRequest.getRole() != null ? registerRequest.getRole().toUpperCase() : "USER";
        Role userRole = roleRepository.findByName("ROLE_" + roleName)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));

        user.setRole(userRole); // Assign the selected role
        userRepository.save(user);

        return ResponseEntity.ok(Map.of("message", "User registered successfully as " + roleName + "!"));
    }





}
