package org.example.plandevelop.comment.service;


import lombok.RequiredArgsConstructor;
import org.example.plandevelop.comment.domain.Comment;
import org.example.plandevelop.comment.domain.dto.CommentCreateRequestDto;
import org.example.plandevelop.comment.domain.dto.CommentResponseDto;
import org.example.plandevelop.comment.repository.CommentRepository;
import org.example.plandevelop.exception.ServiceException;
import org.example.plandevelop.plan.domain.Plan;
import org.example.plandevelop.plan.repository.PlanRepository;
import org.example.plandevelop.user.domain.User;
import org.example.plandevelop.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PlanRepository planRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentResponseDto createComment(Long planId, CommentCreateRequestDto requestDto, Long loginUserId) {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new ServiceException(HttpStatus.NOT_FOUND, "일정을 찾을 수 없습니다."));

        User user = userRepository.findById(loginUserId)
                .orElseThrow(() -> new ServiceException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."));

        long commentCount = commentRepository.countByPlanId(planId);
        if (commentCount >= 10) {
            throw new ServiceException(HttpStatus.BAD_REQUEST, "댓글은 10개까지만 작성할 수 있습니다.");
        }

        Comment comment = new Comment(requestDto.getContent(), user, plan);
        return new CommentResponseDto(commentRepository.save(comment));
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> getComments(Long planId) {
        if (!planRepository.existsById(planId)) {
            throw new ServiceException(HttpStatus.NOT_FOUND, "일정을 찾을 수 없습니다.");
        }
        return commentRepository.findAllByPlanId(planId).stream()
                .map(CommentResponseDto::new)
                .toList();
    }
}