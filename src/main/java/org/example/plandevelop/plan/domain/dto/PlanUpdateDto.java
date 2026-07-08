package org.example.plandevelop.plan.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlanUpdateDto {
    private String title;
    private String content;
    private String password;
}