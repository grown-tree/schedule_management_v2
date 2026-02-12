package com.schedule_management_v2.comment.repository;

import com.schedule_management_v2.comment.entity.Comment;
import com.schedule_management_v2.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAll();

    List<Comment> findAllByScheduleId(Long scheduleId);

    List<Comment> findAllByUserId(Long userId);
}
