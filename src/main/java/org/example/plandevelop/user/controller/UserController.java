package org.example.plandevelop.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.plandevelop.user.domain.dto.UserCreateRequestDto;
import org.example.plandevelop.user.domain.dto.UserDeleteDto;
import org.example.plandevelop.user.domain.dto.UserResponseDto;
import org.example.plandevelop.user.domain.dto.UserUpdateDto;
import org.example.plandevelop.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(
            @RequestBody UserCreateRequestDto requestDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(requestDto));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long id,
            @RequestBody UserUpdateDto requestDto
    ) {
        return ResponseEntity.ok(userService.updateUser(id, requestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id,
            @RequestBody UserDeleteDto deleteDto
    ) {
        userService.deleteUser(id, deleteDto);
        return ResponseEntity.noContent().build();
    }
}