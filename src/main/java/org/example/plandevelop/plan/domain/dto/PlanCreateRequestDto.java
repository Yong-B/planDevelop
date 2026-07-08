package org.example.plandevelop.plan.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlanCreateRequestDto {
    @NotBlank(message = "할 일 제목은 필수 입력 항목입니다.")
    @Size(max = 10, message = "할 일 제목은 10글자 이내여야 합니다.") 
    private String title;

    @NotBlank(message = "할 일 내용은 필수 입력 항목입니다.")
    private String content;

    @NotBlank(message = "일정 비밀번호는 필수 입력 항목입니다.")
    private String password;
}

