package com.schedule_management_v2.schedule.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ScheduleRequestDto {
    @NotBlank(message = "제목은 필수 입력 값입니다.")
    @Size(max = 10, message = "할일 제목은 10글자 이내여야 합니다.")
    private String title;

    @NotBlank(message = "내용은 필수 입력 값입니다.")
    @Size(min = 5, message = "할일 내용은 최소 5글자 이상이어야 합니다.")
    private String content;

    @Min(value = 1, message = "유효한 유저 ID를 입력해주세요.")
    @NotNull(message = "작성자 ID는 필수입니다.")
    @Min(1) // 유저 ID는 양수
    private Long user_id;
    // 작성/수정일은 서버에서 관리하므로 DTO에는 포함x

}
