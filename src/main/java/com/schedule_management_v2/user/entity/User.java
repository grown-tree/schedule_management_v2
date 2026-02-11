package com.schedule_management_v2.user.entity;

import com.schedule_management_v2.schedule.entity.BaseTimeEntity;
import com.schedule_management_v2.schedule.entity.Schedule;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @Column(length = 200, nullable = false)
    private String email;

    //양방향 설정을 위한코드 현재는 필요x
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private List<Schedule> schedules = new ArrayList<>();

//    @Column(length = 200, nullable = false)
//    private String userPassword;

    public User(String userName, String email
//                ,String userPassword
    ) {
        this.userName = userName;
        this.email = email;
//        this.userPassword = userPassword;
    }

    // 유저 수정 (이름, 이메일 수정 가능) 날짜는 자동업뎃
    public void update(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }
}
