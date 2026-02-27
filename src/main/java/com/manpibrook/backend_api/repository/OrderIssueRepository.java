package com.manpibrook.backend_api.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.manpibrook.backend_api.entity.OrderIssue;

@Repository
public interface OrderIssueRepository extends JpaRepository<OrderIssue, Long> {

    List<OrderIssue> findByHandledFalse();
}
