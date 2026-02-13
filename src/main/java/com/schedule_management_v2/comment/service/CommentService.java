package com.schedule_management_v2.comment.service;

import com.schedule_management_v2.comment.dto.CommentRequestDto;
import com.schedule_management_v2.comment.dto.CommentResponseDto;
import com.schedule_management_v2.comment.entity.Comment;
import com.schedule_management_v2.comment.repository.CommentRepository;
import com.schedule_management_v2.schedule.entity.Schedule;
import com.schedule_management_v2.schedule.repository.ScheduleRepository;
import com.schedule_management_v2.user.entity.User;
import com.schedule_management_v2.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    // 댓글 생성
    @Transactional
    public CommentResponseDto save(Long scheduleId, CommentRequestDto requestDto, Long loginUserId) {
        // 1. 해당 일정이 있는지 확인
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("해당 일정이 존재하지 않습니다."));

        // 2. 세션의 유저가 DB에 존재하는지 확인
        User user = userRepository.findById(loginUserId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));

        // 3. 댓글 엔티티 생성 및 저장
        Comment comment = new Comment(
                requestDto.getContent(),
                user,
                schedule
        );
        Comment savedComment = commentRepository.save(comment);

        // 4. 응답 DTO 반환
        return new CommentResponseDto(savedComment);
    }

    // 특정 일정의 전체 댓글 조회
    @Transactional(readOnly = true)
    public List<CommentResponseDto> getAllBySchedule(Long scheduleId) {
        // 일정이 존재하는지 먼저 확인 (선택 사항)
        if (!scheduleRepository.existsById(scheduleId)) {
            throw new IllegalArgumentException("해당 일정이 존재하지 않습니다.");
        }

        List<Comment> commentList = commentRepository.findAllByScheduleId(scheduleId);

        return commentList.stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }
    // 단건 댓글 조회
    @Transactional(readOnly = true)
    public CommentResponseDto getSelectedComment(Long scheduleId, Long commentId) {
        // 일정 존재 확인
        if (!scheduleRepository.existsById(scheduleId)) {
            throw new IllegalArgumentException("해당 ID의 일정이 존재하지 않습니다. id=" + scheduleId);
        }
        // 해당 댓글이 존재하는지 확인 + 댓글에 맞는 일정번호인지도
        Comment comment = commentRepository.findByIdAndSchedule_Id(commentId,scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 댓글이 없습니다. id=" + commentId));

        // DTO로 변환하여 반환
        return new CommentResponseDto(comment);
    }

    // 댓글 수정
    @Transactional
    public CommentResponseDto updateComment(Long scheduleId, Long commentId, CommentRequestDto requestDto, Long loginUserId) {
        // 일정 존재 확인
        if (!scheduleRepository.existsById(scheduleId)) {
            throw new IllegalArgumentException("해당 ID의 일정이 존재하지 않습니다. id=" + scheduleId);
        }
        // 1. 해당 댓글이 존재하는지 확인
        Comment comment = commentRepository.findByIdAndSchedule_Id(commentId,scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        // 2. 댓글 작성자의 ID와 세션에서 넘어온 loginUserId 비교 (권한 확인)
        if (!comment.getUser().getId().equals(loginUserId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인이 작성한 댓글만 수정할 수 있습니다.");
        }

        // 3. 엔티티의 update 메서드 호출 (Dirty Checking 활용)
        comment.updateContent(requestDto.getContent());

        // 4. 응답 DTO 반환
        return new CommentResponseDto(comment);
    }

    // 댓글 삭제
    @Transactional
    public void deleteComment(Long scheduleId, Long commentId, Long loginUserId) {
        // 일정 존재 확인
        if (!scheduleRepository.existsById(scheduleId)) {
            throw new IllegalArgumentException("해당 ID의 일정이 존재하지 않습니다. id=" + scheduleId);
        }
        // 1. 해당 댓글이 존재하는지 확인
        Comment comment = commentRepository.findByIdAndSchedule_Id(commentId,scheduleId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        // 2. 작성자 본인 확인
        if (!comment.getUser().getId().equals(loginUserId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인이 작성한 댓글만 삭제할 수 있습니다.");
        }

        // 3. 삭제 수행
        commentRepository.delete(comment);
    }
}
