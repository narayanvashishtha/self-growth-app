package com.system_mastery.ascension.dto;

import lombok.Data;

@Data
public class AuthResponseDto {

    private String token;

    private String tokenType = "Bearer";

    private long expiresIn;

    public AuthResponseDto(String token, long expirationInMs) {
        this.token = token;
    }
}
