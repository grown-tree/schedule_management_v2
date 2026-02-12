package com.schedule_management_v2.schedule.controller;

import com.schedule_management_v2.schedule.dto.ScheduleRequestDto;
import com.schedule_management_v2.schedule.dto.ScheduleResponseDto;
import com.schedule_management_v2.schedule.service.ScheduleService;
import com.schedule_management_v2.user.dto.SessionUserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/schedules")//공통경로
@RequiredArgsConstructor
@Validated //@PathVariable, @RequestParam 검증
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> create(@Valid @RequestBody ScheduleRequestDto request) {
        ScheduleResponseDto result = scheduleService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping
    public  ResponseEntity<List<ScheduleResponseDto>> getSchedules() {
        List<ScheduleResponseDto> schedules = scheduleService.getAll();
        return ResponseEntity.ok(schedules);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> getScheduleById(@PathVariable Long id) {

        ScheduleResponseDto schedule = scheduleService.getSelectedSchedule(id);
        return ResponseEntity.ok(schedule);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable Long id,
            @Valid
            @RequestBody ScheduleRequestDto requestDto,
            @SessionAttribute(name = "loginUser",required = false) SessionUserDto loginUser) {
        //@SessionAttribute 를 통해 세션이 있으면 해당값을 loginUserId에 주입
        ScheduleResponseDto result = scheduleService.updateSchedule(id, requestDto, loginUser.getId());

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long id,
            @RequestBody ScheduleRequestDto requestDto,
            @SessionAttribute(name = "loginUser", required = false) SessionUserDto loginUser) {

        // 세션이 만료되었거나 로그인이 안 된 경우 예외 처리
        if (loginUser == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }
        scheduleService.deleteSchedule(id, requestDto,loginUser.getId() );
        return ResponseEntity.noContent().build();
    }



}
