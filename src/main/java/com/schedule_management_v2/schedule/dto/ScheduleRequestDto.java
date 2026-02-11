package com.schedule_management_v2.schedule.dto;

import lombok.Getter;

@Getter
public class ScheduleRequestDto {
    private String title;
    private String content;
    private Long user_id;
//    private String password;
    // 작성/수정일은 서버에서 관리하므로 DTO에는 포함x

    public class ScheduleUpdateRequestDto {
        private String title;
        private String author;
//        private String password;
    }
}
