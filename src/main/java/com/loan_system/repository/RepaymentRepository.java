package com.loan_system.repository;

import com.loan_system.entity.Repayment;
import com.loan_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepaymentRepository extends JpaRepository<Repayment, Long> {
    List<Repayment> findByUser(User user);
}
