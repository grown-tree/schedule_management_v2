package com.schedule_management_v2.user.dto;

import lombok.Getter;

@Getter
public class LoginRequestDto {

    private String email;
    private String userPassword;

    public LoginRequestDto(String email, String password) {
        this.email = email;
        this.userPassword = password;
    }
}
