package org.example.plandevelop.plan.repository;

import org.example.plandevelop.plan.domain.Plan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {

    Page<Plan> findAllByOrderByModifiedAtDesc(Pageable pageable);

    Page<Plan> findAllByUserIdOrderByModifiedAtDesc(Long userId, Pageable pageable);

}