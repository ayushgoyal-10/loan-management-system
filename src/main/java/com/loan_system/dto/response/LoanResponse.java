package com.loan_system.dto.response;

import com.loan_system.entity.LoanStatus;

import java.time.LocalDateTime;

public class LoanResponse {
    private Long id;
    private String username;
    private Double amount;
    private Integer tenureMonths;
    private String purpose;
    private LoanStatus status;
    private String rejectionResponse;
    private LocalDateTime appliedAt;
}
