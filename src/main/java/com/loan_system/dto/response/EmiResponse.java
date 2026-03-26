package com.loan_system.dto.response;

import com.loan_system.entity.EmiStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmiResponse {
    private Long id;
    private Integer emiNumber;
    private Double emiAmount;
    private LocalDate dueDate;
    private EmiStatus emiStatus;
    private Double penalty;
}
