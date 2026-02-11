package com.schedule_management_v2.schedule.repository;

import com.schedule_management_v2.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    // 전체 조회
    List<Schedule> findAll();
}
