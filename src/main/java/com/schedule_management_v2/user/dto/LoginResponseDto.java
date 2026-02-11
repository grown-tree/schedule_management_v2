package com.schedule_management_v2.user.dto;

import lombok.Getter;

@Getter
public class LoginResponseDto {
    private final Long id;
    private final String email;
    private final String userName;

    public LoginResponseDto(Long id, String email, String userName) {
        this.id = id;
        this.email = email;
        this.userName = userName;
    }
}
