package com.schedule_management_v2.schedule.service;

import com.schedule_management_v2.schedule.dto.ScheduleRequestDto;
import com.schedule_management_v2.schedule.dto.ScheduleResponseDto;
import com.schedule_management_v2.schedule.entity.Schedule;
import com.schedule_management_v2.schedule.repository.ScheduleRepository;
import com.schedule_management_v2.user.entity.User;
import com.schedule_management_v2.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Handler;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    //일정 생성
    @Transactional
    public ScheduleResponseDto save(ScheduleRequestDto requestDto){

        User user = userRepository.findById(requestDto.getUser_id())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));

        Schedule schedule =  new Schedule(
                requestDto.getTitle(),
                requestDto.getContent(),
                user
        );
        Schedule saveSchedule = scheduleRepository.save(schedule);
        return new ScheduleResponseDto(
                saveSchedule.getId(),
                saveSchedule.getTitle(),
                saveSchedule.getContent(),
                user,
                saveSchedule.getCreatedDate(),
                saveSchedule.getUpdatedDate()
        );
    }

    //일정 조회 : 전체조회, 작성자로 조회 (기본값 수정일 기준 내림차순 정렬)
    @Transactional(readOnly = true)
    public List<ScheduleResponseDto> getAll(){
        List<Schedule> scheduleList = scheduleRepository.findAll();

        //DTO로 변환 하여 반환(비밀번호제외됨)
        return scheduleList.stream().map(schedule -> new ScheduleResponseDto(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getUser(),
                schedule.getCreatedDate(),
                schedule.getUpdatedDate()
        )).collect(Collectors.toList());
    }

    //선택 일정 조회
    @Transactional(readOnly = true)
    public ScheduleResponseDto getSelectedSchedule(Long id) {
        // id에 해당하는 일정을 찾고, 없으면 예외 처리
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 일정이 없습니다. " + id));

        // DTO로 변환하여 반환
        return new ScheduleResponseDto(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getUser(),
                schedule.getCreatedDate(),
                schedule.getUpdatedDate()
        );
    }

    //일정 수정
    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto requestDto, Long loginUserId) {
        // 1. 해당 일정이 있는지 조회
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 일정이 존재하지 않습니다."));

        // 2-1. 일정 작성자의 ID와 세션에서 넘어온 loginUserId 비교
        if (!schedule.getUser().getId().equals(loginUserId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인의 일정만 수정할 수 있습니다.");
        }
        // 2-2. 해당 일정을 쓴 유저가있는지 확인
        User user = userRepository.findById(requestDto.getUser_id())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        // 3. 엔티티의 update 메서드 호출 (제목, 작성자 내용 변경)
        schedule.update(requestDto.getTitle(),user, requestDto.getContent());

        // 4. 응답 DTO 반환
        return new ScheduleResponseDto(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getUser(),
                schedule.getCreatedDate(),
                schedule.getUpdatedDate()
        );
    }

    //일정 삭제
    @Transactional
    public void deleteSchedule(Long id, ScheduleRequestDto requestDto,Long loginUserId) {

        //일정 존재하는지 확인
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 일정이 존재하지 않습니다."));

        //일정작성자id와 세션유저id비교
        if (!schedule.getUser().getId().equals(loginUserId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인의 일정만 삭제할 수 있습니다.");
        }

        scheduleRepository.delete(schedule);
    }
}
