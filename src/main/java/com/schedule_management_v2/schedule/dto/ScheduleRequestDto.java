package com.schedule_management_v2.schedule.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ScheduleRequestDto {
    private String title;
    private String content;
    @NotNull(message = "작성자 ID는 필수입니다.")
    @Min(1) // 유저 ID는 양수
    private Long user_id;
//    private String password;
    // 작성/수정일은 서버에서 관리하므로 DTO에는 포함x

    public class ScheduleUpdateRequestDto {
        private String title;
        private String author;
//        private String password;
    }
}
