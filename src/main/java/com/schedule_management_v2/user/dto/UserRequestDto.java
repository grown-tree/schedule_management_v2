package com.schedule_management_v2.user.dto;

import lombok.Getter;

@Getter
public class UserRequestDto {
    private String userName;
    private String email;
    //private String userPassword;
    // 작성/수정일은 서버에서 관리하므로 DTO에는 포함x

    public class UserUpdateRequestDto {
        private String userName;
        private String email;
        //private String userPassword;
    }
}
