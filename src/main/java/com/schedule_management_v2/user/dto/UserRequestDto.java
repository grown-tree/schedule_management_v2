package com.schedule_management_v2.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserRequestDto {
    @NotBlank
    private String userName;
    @Email // 이메일 형식 검증
    @NotBlank
    private String email;
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    @NotBlank
    private String userPassword;

    // 작성/수정일은 서버에서 관리하므로 DTO에는 포함x

    public class UserUpdateRequestDto {
        private String userName;
        private String email;
        private String userPassword;
    }
}
