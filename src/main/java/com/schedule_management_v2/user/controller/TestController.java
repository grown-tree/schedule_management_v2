package com.schedule_management_v2.user.controller;

import com.schedule_management_v2.user.dto.SessionUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequiredArgsConstructor
public class TestController {
    @GetMapping("/test")
    public ResponseEntity<String> test(@SessionAttribute(name = "loginUser",required = false) SessionUserDto sessionUser) {
        if (sessionUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
