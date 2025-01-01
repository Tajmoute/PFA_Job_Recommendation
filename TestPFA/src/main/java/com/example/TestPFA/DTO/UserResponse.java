package com.example.TestPFA.DTO;

public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String phoneNumber;
    private String role;

    // Constructors
    public UserResponse() {}

    public UserResponse(Long id, String username, String email, String phoneNumber, String role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    // Getters and Setters
    // ... (omitted for brevity)
}
