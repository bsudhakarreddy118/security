package com.example.security.service;

import com.example.security.entity.LoginRequest;
import com.example.security.entity.RegistrationRequest;
import com.example.security.entity.TokenResponse;
import com.example.security.entity.Users;
import com.example.security.repository.UserRepository;
import com.example.security.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService{

    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Override
    public void registerUser(RegistrationRequest request) {

        Users user=new Users();
        user.setUsername(request.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));
        this.userRepository.save(user);
    }

    @Override
    public TokenResponse loginUser(LoginRequest loginRequest) {
        try
        {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        }
        catch (BadCredentialsException ex)
        {
            throw new UsernameNotFoundException("User not available");
        }
        String token = JwtUtil.generateToken(loginRequest);
        TokenResponse jwttoken=new TokenResponse();
        jwttoken.setToken(token);
        return jwttoken;
    }

}


