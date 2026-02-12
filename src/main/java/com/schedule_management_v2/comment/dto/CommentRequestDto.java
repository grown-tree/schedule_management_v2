package com.schedule_management_v2.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentRequestDto {
    @NotBlank(message = "댓글은 필수 입력 값입니다.")
    private String content;

}
