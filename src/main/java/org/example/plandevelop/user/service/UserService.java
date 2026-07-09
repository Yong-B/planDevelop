package org.example.plandevelop.user.service;

import lombok.RequiredArgsConstructor;
import org.example.plandevelop.PasswordEncoder;
import org.example.plandevelop.exception.ServiceException;
import org.example.plandevelop.user.domain.User;
import org.example.plandevelop.user.domain.dto.*;
import org.example.plandevelop.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDto createUser(UserCreateRequestDto requestDto) {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new ServiceException(HttpStatus.CONFLICT, "이미 가입된 이메일입니다.");
        }
        
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        User user = new User(requestDto.getUsername(), requestDto.getEmail(), encodedPassword);  // encodedPassword로 수정
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
                .orElseThrow(() -> new ServiceException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."));
        return new UserResponseDto(user);
    }

    @Transactional
    public UserResponseDto updateUser(Long id, UserUpdateDto requestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ServiceException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."));

        user.update(requestDto.getUsername(), requestDto.getEmail());
        return new UserResponseDto(user);
    }

    @Transactional
    public void deleteUser(Long id, UserDeleteDto deleteDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ServiceException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(deleteDto.getPassword(), user.getPassword())) {
            throw new ServiceException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }
        userRepository.delete(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDto login(UserLoginRequestDto requestDto) {
        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new ServiceException(HttpStatus.BAD_REQUEST, "이메일 또는 비밀번호가 올바르지 않습니다."));

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new ServiceException(HttpStatus.BAD_REQUEST, "이메일 또는 비밀번호가 올바르지 않습니다.");
        }
        return new UserResponseDto(user);
    }
}