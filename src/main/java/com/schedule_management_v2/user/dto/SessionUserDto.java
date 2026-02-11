package com.schedule_management_v2.user.dto;

import lombok.Getter;

@Getter
public class SessionUserDto {

    private final Long id;
    private final String email;
    private final String password;

    public SessionUserDto(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }
}
