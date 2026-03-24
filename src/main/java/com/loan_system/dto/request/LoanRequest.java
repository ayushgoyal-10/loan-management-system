package com.loan_system.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanRequest {

    @NotNull(message = "Amount is required")
    @Min(value = 1000, message = "Minimum loan amount is 1000")
    private Double amount;

    @NotNull(message = "Tenure is required")
    @Min(value = 1, message = "Minimum tenure is 1 month")
    @Max(value = 120, message = "Maximum tenure is 120 months")
    private Integer tenureMonths;

    @NotBlank(message = "Purpose is required")
    private String purpose;
}
