package com.example.security.service;


import com.example.security.entity.LoginRequest;
import com.example.security.entity.RegistrationRequest;
import com.example.security.entity.TokenResponse;

public interface AuthService {
    void registerUser(RegistrationRequest request);
    TokenResponse loginUser(LoginRequest request) throws Exception;
}
