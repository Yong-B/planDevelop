package org.example.plandevelop.comment.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentCreateRequestDto {
    @NotBlank(message = "댓글 내용은 필수 입력 항목입니다.")
    private String content;
}
