package org.example.plandevelop.plan.service;

import lombok.RequiredArgsConstructor;
import org.example.plandevelop.comment.repository.CommentRepository;
import org.example.plandevelop.exception.ServiceException;
import org.example.plandevelop.plan.domain.Plan;
import org.example.plandevelop.plan.domain.dto.PlanCreateRequestDto;
import org.example.plandevelop.plan.domain.dto.PlanDeleteDto;
import org.example.plandevelop.plan.domain.dto.PlanResponseDto;
import org.example.plandevelop.plan.domain.dto.PlanUpdateDto;
import org.example.plandevelop.plan.repository.PlanRepository;
import org.example.plandevelop.user.domain.User;
import org.example.plandevelop.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanService {

    private final PlanRepository planRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public PlanResponseDto createPlan(PlanCreateRequestDto requestDto, Long loginUserId) {
        User user = userRepository.findById(loginUserId)
                .orElseThrow(() -> new ServiceException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."));

        Plan plan = new Plan(requestDto.getTitle(), requestDto.getContent(), user, requestDto.getPassword());
        Plan savedPlan = planRepository.save(plan);
        return new PlanResponseDto(savedPlan, 0);
    }

    @Transactional(readOnly = true)
    public Page<PlanResponseDto> getPlans(Long userId, Pageable pageable) {
        Page<Plan> plans = (userId == null)
                ? planRepository.findAllByOrderByModifiedAtDesc(pageable)
                : planRepository.findAllByUserIdOrderByModifiedAtDesc(userId, pageable);

        return plans.map(plan -> new PlanResponseDto(plan, commentRepository.countByPlanId(plan.getId())));
    }

    @Transactional(readOnly = true)
    public PlanResponseDto getPlan(Long planId) {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new ServiceException(HttpStatus.NOT_FOUND, "일정을 찾을 수 없습니다."));
        return new PlanResponseDto(plan, commentRepository.countByPlanId(planId));
    }

    @Transactional
    public PlanResponseDto updateDto(Long id, PlanUpdateDto updateDto) {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new ServiceException(HttpStatus.NOT_FOUND, "일정을 찾을 수 없습니다."));

        plan.validatePassword(updateDto.getPassword());
        plan.update(updateDto.getTitle(), updateDto.getContent());

        return new PlanResponseDto(plan, commentRepository.countByPlanId(id));
    }

    @Transactional
    public void deletePlan(Long id, PlanDeleteDto deleteDto) {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new ServiceException(HttpStatus.NOT_FOUND, "일정을 찾을 수 없습니다."));

        plan.validatePassword(deleteDto.getPassword());
        planRepository.delete(plan);
    }
}