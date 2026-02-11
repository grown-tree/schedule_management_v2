package com.schedule_management_v2.user.repository;

import com.schedule_management_v2.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    // 전체 조회
    List<User> findAll();
}
