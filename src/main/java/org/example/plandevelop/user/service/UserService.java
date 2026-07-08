package org.example.plandevelop.user.service;


import lombok.RequiredArgsConstructor;
import org.example.plandevelop.user.domain.User;
import org.example.plandevelop.user.domain.dto.UserCreateRequestDto;
import org.example.plandevelop.user.domain.dto.UserDeleteDto;
import org.example.plandevelop.user.domain.dto.UserResponseDto;
import org.example.plandevelop.user.domain.dto.UserUpdateDto;
import org.example.plandevelop.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponseDto createUser(UserCreateRequestDto requestDto) {
        User user = new User(requestDto.getUsername(), requestDto.getEmail());
        return new UserResponseDto(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> getUsers() {
        return userRepository.findAll().stream()
                .map(UserResponseDto::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserResponseDto getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
        return new UserResponseDto(user);
    }

    @Transactional
    public UserResponseDto updateUser(Long id, UserUpdateDto requestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        user.update(requestDto.getUsername(), requestDto.getEmail());
        return new UserResponseDto(user);
    }

    @Transactional
    public void deleteUser(Long id, UserDeleteDto deleteDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
        userRepository.delete(user);
    }
}