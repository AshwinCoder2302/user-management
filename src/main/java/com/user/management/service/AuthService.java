package com.user.management.service;

import com.user.management.dto.LoginRequestDTO;
import com.user.management.dto.LoginResponseDTO;
import com.user.management.dto.SignupRequestDTO;

public interface AuthService {

    LoginResponseDTO authenticateUser(LoginRequestDTO loginRequest);

    String registerUser(SignupRequestDTO signupRequest);
}
