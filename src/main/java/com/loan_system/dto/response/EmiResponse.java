package com.loan_system.dto.response;

import com.loan_system.entity.EmiStatus;

import java.time.LocalDate;

public class EmiResponse {
    private Long id;
    private Integer emiNumber;
    private Double emiAmount;
    private LocalDate dueDate;
    private EmiStatus emiStatus;
    private Double penalty;
}
