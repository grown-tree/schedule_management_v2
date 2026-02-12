package com.schedule_management_v2.schedule.entity;

import com.schedule_management_v2.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Entity
@Table(name = "schedules")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends BaseTimeEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        //연관관계
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")//유저테이블과 매핑
        @OnDelete(action = OnDeleteAction.CASCADE)
        private User user;

        @Column(length = 30, nullable = false)
        @Size(max = 10, message = "할일 제목은 10글자 이내여야 합니다.")
        @NotBlank
        private String title;

        @Column(length = 200)
        @Size(min = 5, message = "할일 내용은은 최소 5글자 이상이여야 합니다.")
        @NotBlank
        private String content;

        public Schedule(String title, String content, User user) {
                this.title = title;
                this.content = content;
                this.user = user;
        }
        // 일정 수정 (제목, 작성자명만 수정 가능) 날짜는 자동업뎃
        public void update(String title, User user,String content) {
                this.title = title;
                this.user = user;
                this.content = content;
        }


}
