package com.loan_system.repository;

import com.loan_system.entity.LoanApplication;
import com.loan_system.entity.LoanStatus;
import com.loan_system.entity.User;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {
    List<LoanApplication> findByStatus(LoanStatus status);
    List<LoanApplication> findByUser(User user);
}
