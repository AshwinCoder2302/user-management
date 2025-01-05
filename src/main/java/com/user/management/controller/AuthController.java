package com.user.management.controller;

import com.user.management.dto.*;
import com.user.management.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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

    @GetMapping("/access-token")
    public ResponseDTO<AccessTokenResponseDTO> getAccessToken(@RequestParam String refreshToken) throws IOException {
        return new ResponseDTO<>(HttpStatus.OK, "Success",authService.getAccessToken(refreshToken));
    }
}