package org.example.plandevelop.comment.domain.dto;

import lombok.Getter;
import org.example.plandevelop.comment.domain.Comment;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long id;
    private Long planId;
    private String content;
    private String authorName;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.planId = comment.getPlan().getId();
        this.content = comment.getContent();
        this.authorName = comment.getUser().getUsername();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}