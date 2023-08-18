package com.example.security.entity;

import lombok.Data;

@Data
public class RegistrationRequest {
    private String username;
    private String password;
    private String email;

}