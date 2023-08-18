package com.example.security.controller;

import com.example.security.entity.LoginRequest;
import com.example.security.entity.RegistrationRequest;
import com.example.security.entity.TokenResponse;
import com.example.security.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
 @Autowired
 private AuthService authService;
 @PostMapping("/register")
 public ResponseEntity<String> registerUser(@RequestBody RegistrationRequest request) {
  authService.registerUser(request);
  return ResponseEntity.ok("User registered successfully.");
 }
 @PostMapping("/login")
 public ResponseEntity<TokenResponse> loginUser(@RequestBody LoginRequest request) throws Exception {
  TokenResponse token = authService.loginUser(request);
  return ResponseEntity.ok(token);
 }
 @GetMapping("test")
 public String getTestValue()
 {

  return "able to access";
 }
}
