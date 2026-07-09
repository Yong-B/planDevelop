package org.example.plandevelop.comment.repository;

import org.example.plandevelop.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    long countByPlanId(Long planId);

    List<Comment> findAllByPlanId(Long planId);
}
