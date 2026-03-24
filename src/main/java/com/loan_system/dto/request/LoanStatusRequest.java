package com.loan_system.dto.request;

import com.loan_system.entity.LoanStatus;
import jakarta.validation.constraints.NotNull;

public class LoanStatusRequest {

    @NotNull(message = "Status is required")
    private LoanStatus status;

    private String rejectionReason;
}
