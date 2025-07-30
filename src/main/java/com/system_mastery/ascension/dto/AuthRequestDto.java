package com.system_mastery.ascension.dto;

import lombok.Data;

@Data
public class AuthRequestDto {

    private String username;
    private String password;
    private String email;

    public AuthRequestDto(String password, String username, String email) {
        this.password = password;
        this.username = username;
        this.email = email;
    }

}
