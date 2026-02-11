package com.schedule_management_v2.schedule.controller;

import com.schedule_management_v2.schedule.dto.ScheduleRequestDto;
import com.schedule_management_v2.schedule.dto.ScheduleResponseDto;
import com.schedule_management_v2.schedule.service.ScheduleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/schedules")//공통경로
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> create(@RequestBody ScheduleRequestDto request) {
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
            @RequestBody ScheduleRequestDto requestDto,
            HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginUser") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Long loginUserId = (Long) session.getAttribute("loginUser");

        ScheduleResponseDto result = scheduleService.updateSchedule(id, requestDto, loginUserId);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long id,
            @RequestBody ScheduleRequestDto requestDto,
            @SessionAttribute(name = "loginUser", required = false) Long loginUserId) {

        // 세션이 만료되었거나 로그인이 안 된 경우 예외 처리
        if (loginUserId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }
        scheduleService.deleteSchedule(id, requestDto,loginUserId );
        return ResponseEntity.noContent().build();
    }



}
