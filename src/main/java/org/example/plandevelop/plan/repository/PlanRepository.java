package org.example.plandevelop.plan.repository;

import org.example.plandevelop.plan.domain.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
    List<Plan> findAllByOrderByModifiedAtDesc();

    List<Plan> findAllByUserIdOrderByModifiedAtDesc(Long userId);

}