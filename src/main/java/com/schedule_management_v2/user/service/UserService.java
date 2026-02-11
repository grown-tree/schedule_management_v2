package com.schedule_management_v2.user.service;


import com.schedule_management_v2.user.dto.UserRequestDto;
import com.schedule_management_v2.user.dto.UserResponseDto;
import com.schedule_management_v2.user.entity.User;
import com.schedule_management_v2.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    //유저 생성
    @Transactional
    public UserResponseDto save(UserRequestDto userRequestDto){
        User user = new User(
                userRequestDto.getUserName(),
                userRequestDto.getEmail()
//                userRequestDto.getUserPassword()
        );
        User savedUser = userRepository.save(user);
        return new UserResponseDto(
                savedUser.getId(),
                savedUser.getUserName(),
                savedUser.getEmail(),
                savedUser.getCreatedDate(),
                savedUser.getUpdatedDate()
        );


    }

    //유저 조회 : 전체조회
    @Transactional(readOnly = true)
    public List<UserResponseDto> getAll(){
        List<User> userList = userRepository.findAll();

        return userList.stream().map(schedule -> new UserResponseDto(
                schedule.getId(),
                schedule.getUserName(),
                schedule.getEmail(),
                schedule.getCreatedDate(),
                schedule.getUpdatedDate()
        )).collect(Collectors.toList());
    }

    //선택 유저 조회
    @Transactional(readOnly = true)
    public UserResponseDto getSelectedUser(Long id) {
        // id에 해당하는 유저을 찾고, 없으면 예외 처리
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 유저가 없습니다. " + id));

        // DTO로 변환하여 반환
        return new UserResponseDto(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getCreatedDate(),
                user.getUpdatedDate()
        );
    }

    //유저 수정
    @Transactional
    public UserResponseDto updateUser(Long id, UserRequestDto requestDto) {
        // 1. 해당 유저 있는지 조회
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        // 2. 엔티티의 update 메서드 호출 (이름, 이메일변경)
        user.update(requestDto.getUserName(), requestDto.getEmail());

        // 3. 응답 DTO 반환
        return new UserResponseDto(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getCreatedDate(),
                user.getUpdatedDate()
        );
    }

    //유저 삭제
    @Transactional
    public void deleteUser(Long id, UserRequestDto requestDto) {
        //유저 존재하는지 확인
        User schedule = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        userRepository.delete(schedule);
    }
}
