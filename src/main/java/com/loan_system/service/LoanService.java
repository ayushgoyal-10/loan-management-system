package com.loan_system.service;

import com.loan_system.dto.request.LoanRequest;
import com.loan_system.dto.request.LoanStatusRequest;
import com.loan_system.dto.response.LoanResponse;

import java.util.List;

public interface LoanService {

    LoanResponse applyLoan(Long userId, LoanRequest request);
    LoanResponse updateLoanStatus(Long loanId, LoanStatusRequest request);
    List<LoanResponse> getLoanByUser(Long userId);
    List<LoanResponse> getAllLoans();
}
