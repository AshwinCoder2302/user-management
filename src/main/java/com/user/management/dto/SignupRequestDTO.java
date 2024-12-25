package com.user.management.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignupRequestDTO {
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Password is required")
    private String password;
    @NotBlank(message = "Full Name is required")
    private String fullName;
    @NotBlank(message = "Mobile Number is required")
    private String mobileNo;
}
