package org.example.plandevelop.plan.domain.dto;

import lombok.Getter;
import org.example.plandevelop.plan.domain.Plan;

import java.time.LocalDateTime;

@Getter
public class PlanResponseDto {
    private final Long id;
    private final String title;
    private final String content;
    private String authorName;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public PlanResponseDto(Plan plan) {
        this.id = plan.getId();
        this.title = plan.getTitle();
        this.content = plan.getContent();
        this.authorName = plan.getUser().getUsername();
        this.createdAt = plan.getCreatedAt();
        this.modifiedAt = plan.getModifiedAt();
    }
}