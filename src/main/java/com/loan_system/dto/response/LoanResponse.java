package com.loan_system.dto.response;

import com.loan_system.entity.LoanStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanResponse {
    private Long id;
    private String userName;
    private Double amount;
    private Integer tenureMonths;
    private String purpose;
    private LoanStatus status;
    private String rejectionResponse;
    private LocalDateTime appliedAt;
}
