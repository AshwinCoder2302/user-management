package com.user.management.dto;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class AccessTokenResponseDTO {
    private String accessToken;
}
