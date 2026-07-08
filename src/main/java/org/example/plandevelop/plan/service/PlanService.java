package org.example.plandevelop.plan.service;

import lombok.RequiredArgsConstructor;
import org.example.plandevelop.plan.domain.Plan;
import org.example.plandevelop.plan.domain.dto.PlanCreateRequestDto;
import org.example.plandevelop.plan.domain.dto.PlanDeleteDto;
import org.example.plandevelop.plan.domain.dto.PlanResponseDto;
import org.example.plandevelop.plan.domain.dto.PlanUpdateDto;
import org.example.plandevelop.plan.repository.PlanRepository;
import org.example.plandevelop.user.domain.User;
import org.example.plandevelop.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanService {
    private final PlanRepository planRepository;
    private final UserRepository userRepository;

    @Transactional
    public PlanResponseDto createPlan(PlanCreateRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        Plan plan = new Plan(
                requestDto.getTitle(),
                requestDto.getContent(),
                user,
                requestDto.getPassword()
        );

        return new PlanResponseDto(planRepository.save(plan));
    }

    @Transactional(readOnly = true)
    public List<PlanResponseDto> getPlans(Long userId) {
        List<Plan> plans = (userId == null)
                ? planRepository.findAllByOrderByModifiedAtDesc()
                : planRepository.findAllByUserIdOrderByModifiedAtDesc(userId);

        return plans.stream()
                .map(PlanResponseDto::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public PlanResponseDto getPlan(Long planId) {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("일정을 찾을 수 없습니다."));

        return new PlanResponseDto(plan);
    }

    @Transactional
    public PlanResponseDto updateDto(Long id, PlanUpdateDto updateDto) {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("일정을 찾을 수 없습니다."));

        plan.validatePassword(updateDto.getPassword());
        plan.update(updateDto.getTitle(), updateDto.getContent());

        return new PlanResponseDto(plan);
    }

    @Transactional
    public void deletePlan(Long id, PlanDeleteDto deleteDto) {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("일정을 찾을 수 없습니다."));

        plan.validatePassword(deleteDto.getPassword());
        planRepository.delete(plan);
    }
}