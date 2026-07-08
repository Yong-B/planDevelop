package org.example.plandevelop.plan.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.plandevelop.plan.domain.dto.PlanCreateRequestDto;
import org.example.plandevelop.plan.domain.dto.PlanDeleteDto;
import org.example.plandevelop.plan.domain.dto.PlanResponseDto;
import org.example.plandevelop.plan.domain.dto.PlanUpdateDto;
import org.example.plandevelop.plan.service.PlanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plans")
@RequiredArgsConstructor
public class PlanController {
    private final PlanService planService;

    @PostMapping
    public ResponseEntity<PlanResponseDto> createPlan(
           @Valid @RequestBody PlanCreateRequestDto requestDto,
            HttpServletRequest request 
    ) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("LOGIN_USER") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Long loginUserId = (Long) session.getAttribute("LOGIN_USER");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(planService.createPlan(requestDto, loginUserId));
    }

    @GetMapping
    public ResponseEntity<List<PlanResponseDto>> getPlans(
            @RequestParam(required = false) Long userId
    ) {
        return ResponseEntity.ok(planService.getPlans(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanResponseDto> getPlan(@PathVariable Long id) {
        return ResponseEntity.ok(planService.getPlan(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PlanResponseDto> updatePlan(
            @PathVariable Long id,
            @RequestBody PlanUpdateDto updateDto
    ) {
        return ResponseEntity.ok(planService.updateDto(id, updateDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlan(
            @PathVariable Long id,
            @RequestBody PlanDeleteDto deleteDto
    ) {
        planService.deletePlan(id, deleteDto);
        return ResponseEntity.noContent().build();
    }
}