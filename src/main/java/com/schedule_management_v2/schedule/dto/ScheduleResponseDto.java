package com.schedule_management_v2.schedule.dto;

import com.schedule_management_v2.user.dto.UserResponseDto;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleResponseDto {
    private Long id;
    private String title;
    private String content;
    private UserResponseDto user;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public ScheduleResponseDto(Long id, String title, String content, UserResponseDto user, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }


}
