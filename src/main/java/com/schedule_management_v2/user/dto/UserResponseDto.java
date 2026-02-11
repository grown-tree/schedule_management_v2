package com.schedule_management_v2.user.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserResponseDto {
    private Long id;
    private String userName;
    private String email;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public UserResponseDto(Long id, String userName, String email,LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }


}
