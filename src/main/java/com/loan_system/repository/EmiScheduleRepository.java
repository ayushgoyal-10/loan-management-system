package com.loan_system.repository;

import com.loan_system.dto.response.EmiResponse;
import com.loan_system.entity.EmiSchedule;
import com.loan_system.entity.EmiStatus;
import com.loan_system.entity.LoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmiScheduleRepository extends JpaRepository<EmiSchedule, Long> {
    List<EmiSchedule> findByEmiStatus(EmiStatus status);
    List<EmiSchedule> findByLoan(LoanApplication loan);
    Optional<EmiSchedule> findByLoanAndEmiNumber(LoanApplication loan, Integer emiNumber);
}
