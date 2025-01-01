package com.example.TestPFA.controller;

import com.example.TestPFA.entity.Role;
import com.example.TestPFA.entity.User;
import com.example.TestPFA.repository.RoleRepository;
import com.example.TestPFA.repository.UserRepository;
import com.example.TestPFA.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User userRequest) {
        // Assign default or provided role
        String roleName = userRequest.getRole().getName() != null ? userRequest.getRole().getName().toUpperCase() : "USER";
        Role role = roleRepository.findByName("ROLE_" + roleName)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));

        userRequest.setRole(role);
        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword())); // Encode the password
        User savedUser = userService.createUser(userRequest);

        return ResponseEntity.ok(Map.of("message", "User created successfully", "user", savedUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User userRequest) {
        String roleName = userRequest.getRole().getName() != null ? userRequest.getRole().getName().toUpperCase() : "USER";
        Role role = roleRepository.findByName("ROLE_" + roleName)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));

        userRequest.setRole(role);
        User updatedUser = userService.updateUser(id, userRequest);

        return ResponseEntity.ok(Map.of("message", "User updated successfully", "user", updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
