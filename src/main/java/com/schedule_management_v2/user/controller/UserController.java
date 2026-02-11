package com.schedule_management_v2.user.controller;

import com.schedule_management_v2.user.dto.*;
import com.schedule_management_v2.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<UserResponseDto>> getUsers() {
        List<UserResponseDto> users = userService.getAll();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/{user_id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long user_id) {

        UserResponseDto user = userService.getSelectedUser(user_id);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/{user_id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long user_id,
            @RequestBody UserRequestDto requestDto,
            HttpServletRequest request) {

        //세션있으면 가져오고 없으면 null반환 = 로그인여부확인
        HttpSession session = request.getSession(false);

        // 1. 세션 존재 여부 및 로그인 확인
        if (session == null || session.getAttribute("loginUser") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();//없는경우 401Unauthorized 반환
        }

        //요청과 세션id비교
        Long loginUserId = (Long)session.getAttribute("loginUser");
        if (!loginUserId.equals(user_id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // 403 권한 없음
        }
        UserResponseDto result = userService.updateUser(user_id, requestDto);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{user_id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long user_id,
            @RequestBody UserRequestDto requestDto,
            HttpServletRequest request) {

        //세션있으면 가져오고 없으면 null반환 = 로그인여부확인
        HttpSession session = request.getSession(false);

        // 1. 세션 존재 여부 및 로그인 확인
        if (session == null || session.getAttribute("loginUser") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();//없는경우 401Unauthorized 반환
        }

        //요청과 세션id비교
        Long loginUserId = (Long)session.getAttribute("loginUser");
        if (!loginUserId.equals(user_id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // 403 권한 없음
        }

        userService.deleteUser(user_id, requestDto);
        return ResponseEntity.noContent().build();
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto request, HttpSession session) {
        UserResponseDto user = userService.login(request);
        //세션
        SessionUserDto sessionUser = new SessionUserDto(user.getId(),user.getEmail(),user.getUserName());
        session.setAttribute("loginUser", sessionUser);//실제 로그인처리부분 유저를 넣어주는게 아닌 세션유저넣기!!
        //응답
        LoginResponseDto response = new LoginResponseDto(user.getId(), user.getEmail(),user.getUserName());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    //로그아웃
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@SessionAttribute(name = "loginUser", required = false) SessionUserDto sessionUser, HttpSession session) {
        if (sessionUser == null) {
            return ResponseEntity.badRequest().build();
        }
        session.invalidate();//세션 무효화 = 로그아웃
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }



}
