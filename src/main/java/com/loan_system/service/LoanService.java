package com.loan_system.service;

import com.loan_system.dto.request.LoanRequest;
import com.loan_system.dto.request.LoanStatusRequest;
import com.loan_system.dto.response.LoanResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LoanService {

    LoanResponse applyLoan(Long userId, LoanRequest request);
    LoanResponse updateLoanStatus(Long loanId, LoanStatusRequest request);
    List<LoanResponse> getLoanByUser(Long userId);
    Page<LoanResponse> getAllLoans(Pageable pageable);
}
