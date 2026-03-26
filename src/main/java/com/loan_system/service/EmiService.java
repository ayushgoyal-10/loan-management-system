package com.loan_system.service;

import com.loan_system.dto.response.EmiResponse;
import com.loan_system.entity.LoanApplication;

import java.util.List;

public interface EmiService {
    void generateEmiSchedule(LoanApplication loan);
    List<EmiResponse> getEmisByLoan(Long loanId);
}
