package com.schedule_management_v2.comment.controller;

import com.schedule_management_v2.comment.dto.CommentRequestDto;
import com.schedule_management_v2.comment.dto.CommentResponseDto;
import com.schedule_management_v2.comment.service.CommentService;
import com.schedule_management_v2.user.dto.SessionUserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/schedules/{scheduleId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성
    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(
            @PathVariable Long scheduleId,
            @Valid @RequestBody CommentRequestDto requestDto,
            @SessionAttribute(name = "loginUser", required = false) SessionUserDto loginUser) {

        validateLogin(loginUser);

        CommentResponseDto result = commentService.save(scheduleId, requestDto, loginUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    // 특정 일정의 전체 댓글 조회
    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> getComments(@PathVariable Long scheduleId) {
        List<CommentResponseDto> list = commentService.getAllBySchedule(scheduleId);
        return ResponseEntity.ok(list);
    }

    // 특정 댓글 상세 조회
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> getCommentById(
            @PathVariable Long scheduleId, // URL 구조 유지를 위해 포함
            @PathVariable Long commentId) {

        CommentResponseDto responseDto = commentService.getSelectedComment(commentId);
        return ResponseEntity.ok(responseDto);
    }

    // 댓글 수정
    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @PathVariable Long scheduleId, // URL 일관성 유지
            @PathVariable Long commentId,
            @Valid @RequestBody CommentRequestDto requestDto,
            @SessionAttribute(name = "loginUser", required = false) SessionUserDto loginUser) {

        validateLogin(loginUser);

        CommentResponseDto result = commentService.updateComment(commentId, requestDto, loginUser.getId());
        return ResponseEntity.ok(result);
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long scheduleId,
            @PathVariable Long commentId,
            @SessionAttribute(name = "loginUser", required = false) SessionUserDto loginUser) {

        validateLogin(loginUser);

        commentService.deleteComment(commentId, loginUser.getId());
        return ResponseEntity.noContent().build();
    }

    // 공통 로그인 검증 메서드 (ScheduleController 방식 참고)
    private void validateLogin(SessionUserDto loginUser) {
        if (loginUser == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }
    }
}
