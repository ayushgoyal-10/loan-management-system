package com.loan_system.dto.request;

import com.loan_system.entity.LoanStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanStatusRequest {

    @NotNull(message = "Status is required")
    private LoanStatus status;

    private String rejectionReason;
}
