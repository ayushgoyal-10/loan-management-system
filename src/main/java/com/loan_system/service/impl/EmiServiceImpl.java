package com.loan_system.service.impl;

import com.loan_system.dto.response.EmiResponse;
import com.loan_system.entity.LoanApplication;
import com.loan_system.service.EmiService;

import java.util.List;

public class EmiServiceImpl implements EmiService {


    @Override
    public void generateEmiSchedule(LoanApplication loan) {

    }

    @Override
    public List<EmiResponse> getEmisByLoan(Long loanId) {
        return List.of();
    }
}
