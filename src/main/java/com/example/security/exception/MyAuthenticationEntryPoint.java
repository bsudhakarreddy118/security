package com.example.security.exception;

import com.example.security.entity.ExceptionModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
 public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException, IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed");

    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ExceptionModel> invalidUser(UsernameNotFoundException ex)
    {
        ExceptionModel model=new ExceptionModel(ex.getMessage(), HttpStatus.NOT_FOUND.toString());
        return new ResponseEntity<>(model,HttpStatus.NOT_FOUND);
    }
}
