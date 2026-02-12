package com.schedule_management_v2.comment.dto;

import com.schedule_management_v2.comment.entity.Comment;
import com.schedule_management_v2.schedule.dto.ScheduleResponseDto;
import com.schedule_management_v2.user.dto.UserResponseDto;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

    private Long id;
    private String content;
    private ScheduleResponseDto schedule;
    private UserResponseDto user;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CommentResponseDto(Long id, String content, ScheduleResponseDto schedule, UserResponseDto user, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.content = content;
        this.schedule = schedule;
        this.user = user;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.schedule = new ScheduleResponseDto(comment.getSchedule());
        this.user = new UserResponseDto(comment.getUser());
        this.createdAt = comment.getCreatedDate();
        this.updatedAt = comment.getUpdatedDate();
    }

}
