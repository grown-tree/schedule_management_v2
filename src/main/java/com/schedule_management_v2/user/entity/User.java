package com.schedule_management_v2.user.entity;

import com.schedule_management_v2.schedule.entity.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "Users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String userName;

    @Column(length = 200, unique = true,nullable = false)
    private String email;

    @Column(length = 200, nullable = false)
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    private String userPassword;

    public User(String userName, String email, String userPassword) {
        this.userName = userName;
        this.email = email;
        this.userPassword = userPassword;
    }

    // 유저 수정 (이름, 이메일,비밀번호 수정 가능) 날짜는 자동업뎃
    public void update(String userName, String email, String userPassword) {
        this.userName = userName;
        this.email = email;
        this.userPassword = userPassword;
    }
}
