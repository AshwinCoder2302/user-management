    package com.user.management.dto;

    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.SuperBuilder;

    @EqualsAndHashCode(callSuper = true)
    @Data
    @SuperBuilder
    public class LoginResponseDTO extends AccessTokenResponseDTO {
        private String refreshToken;
    }
