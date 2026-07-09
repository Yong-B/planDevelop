package org.example.plandevelop.comment.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.plandevelop.comment.domain.dto.CommentCreateRequestDto;
import org.example.plandevelop.comment.domain.dto.CommentResponseDto;
import org.example.plandevelop.comment.service.CommentService;
import org.example.plandevelop.exception.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plans/{planId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(
            @PathVariable Long planId,
            @Valid @RequestBody CommentCreateRequestDto requestDto,
            HttpServletRequest request
    ) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("LOGIN_USER") == null) {
            throw new ServiceException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }
        Long loginUserId = (Long) session.getAttribute("LOGIN_USER");

        CommentResponseDto responseDto = commentService.createComment(planId, requestDto, loginUserId);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
    
    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> getComments(@PathVariable Long planId) {
        return ResponseEntity.ok(commentService.getComments(planId));
    }
}