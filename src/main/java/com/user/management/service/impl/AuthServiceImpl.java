package com.user.management.service.impl;

import com.user.management.constant.Constant;
import com.user.management.dto.LoginRequestDTO;
import com.user.management.dto.LoginResponseDTO;
import com.user.management.dto.SignupRequestDTO;
import com.user.management.entity.User;
import com.user.management.exception.BusinessException;
import com.user.management.repository.UserRepository;
import com.user.management.service.AuthService;
import com.user.management.util.JwtUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtil jwtUtils;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Override
    public LoginResponseDTO authenticateUser(LoginRequestDTO loginRequest) {
        Authentication authentication = null;
      try {
          authentication = authenticationManager.authenticate(
                  new UsernamePasswordAuthenticationToken(
                          loginRequest.getUsername(),
                          loginRequest.getPassword()
                  )
          );
      }
      catch (BadCredentialsException e){
          throw new BusinessException(HttpStatus.UNAUTHORIZED, Constant.IN_CORRECT_PASSWORD);
      }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String accessToken = jwtUtils.generateAccessToken(userDetails.getUsername());
        String refreshToken = jwtUtils.generateRefreshToken(userDetails.getUsername());
        return new LoginResponseDTO(accessToken, refreshToken);
    }

    @Override
    public String registerUser(SignupRequestDTO signupRequest) {
        User user = new User();
        BeanUtils.copyProperties(signupRequest, user);
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        return Constant.USER_REGISTERED_SUCCESSFULLY;
    }
}
