package com.user.management.dto;

import lombok.Data;

@Data
public class UserRequestDTO {
    private String username;
    private String fullName;
    private String gender;
    private String role;
    private String mobileNo;
    private String email;
}
