package com.schedule_management_v2.schedule.dto;

import com.schedule_management_v2.schedule.entity.Schedule;
import com.schedule_management_v2.user.dto.UserResponseDto;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleResponseDto {
    private Long id;
    private String title;
    private String content;
    private Long commentCount;
    private UserResponseDto user;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String userName;

    public ScheduleResponseDto(Long id, String title, String content, UserResponseDto user, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.user = new UserResponseDto(schedule.getUser());
        this.createdDate = schedule.getCreatedDate();
        this.updatedDate = schedule.getUpdatedDate();
    }

    //페이징 전용 생성자
    public ScheduleResponseDto(Long scheduleId, String title, String content, Long commentCount,
                               LocalDateTime createdDate, LocalDateTime updatedDate, String userName) {
        this.id = scheduleId;
        this.title = title;
        this.content = content;
        this.commentCount = commentCount;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.userName = userName;
    }
}
