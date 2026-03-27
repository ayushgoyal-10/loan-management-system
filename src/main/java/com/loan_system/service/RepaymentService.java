package com.loan_system.service;

import com.loan_system.dto.response.RepaymentResponse;

import java.util.List;

public interface RepaymentService {
    RepaymentResponse payEmi(Long emiId, Long userId);
    List<RepaymentResponse> getRepaymentsByUser(Long userId);
}
