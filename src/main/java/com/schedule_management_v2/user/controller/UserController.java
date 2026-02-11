package com.schedule_management_v2.user.controller;

import com.schedule_management_v2.user.dto.UserRequestDto;
import com.schedule_management_v2.user.dto.UserResponseDto;
import com.schedule_management_v2.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")//공통경로
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> create(@RequestBody UserRequestDto request) {
        UserResponseDto result = userService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping
    public  ResponseEntity<List<UserResponseDto>> getUsers() {
        List<UserResponseDto> users = userService.getAll();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/{user_id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long user_id) {

        UserResponseDto user = userService.getSelectedUser(user_id);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{user_id}")
    public UserResponseDto updateUser(@PathVariable Long user_id, @RequestBody UserRequestDto requestDto) {
        return userService.updateUser(user_id, requestDto);
    }

    @DeleteMapping("/{user_id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long user_id, @RequestBody UserRequestDto requestDto) {
        userService.deleteUser(user_id, requestDto);
        return ResponseEntity.noContent().build();
    }



}
