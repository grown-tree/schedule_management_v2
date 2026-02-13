package com.schedule_management_v2.schedule.repository;

import com.schedule_management_v2.schedule.dto.ScheduleResponseDto;
import com.schedule_management_v2.schedule.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findAll();

    //LEFT JOIN을 통해 댓글이0개인것도 가져올수있음
    @Query("SELECT new com.schedule_management_v2.schedule.dto.ScheduleResponseDto(" +
            "s.id,s.title, s.content, count(c), s.createdDate, s.updatedDate, u.userName) " +
            "FROM Schedule s " +
            "JOIN s.user u " +
            "LEFT JOIN Comment c ON c.schedule = s " +
            "GROUP BY s.id, s.title, s.content, s.createdDate, s.updatedDate, u.userName")
    Page<ScheduleResponseDto> findAllWithCommentCount(Pageable pageable);
}
