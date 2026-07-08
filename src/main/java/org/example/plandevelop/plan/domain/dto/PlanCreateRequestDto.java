package org.example.plandevelop.plan.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlanCreateRequestDto {
    private String title;
    private String content;
    private Long userId;
    private String password;
}

