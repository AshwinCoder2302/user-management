package com.user.management.controller;

import com.user.management.dto.LoginRequestDTO;
import com.user.management.dto.LoginResponseDTO;
import com.user.management.dto.ResponseDTO;
import com.user.management.dto.SignupRequestDTO;
import com.user.management.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseDTO<LoginResponseDTO> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequest) {
        return new ResponseDTO<>(HttpStatus.OK, "Success", authService.authenticateUser(loginRequest));
    }

    @PostMapping("/signup")
    public ResponseDTO<String> registerUser(@Valid @RequestBody SignupRequestDTO signupRequest) {
        return new ResponseDTO<>(HttpStatus.OK, "Success", authService.registerUser(signupRequest));
    }
}