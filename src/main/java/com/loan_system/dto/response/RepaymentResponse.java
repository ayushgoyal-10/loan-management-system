package com.loan_system.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RepaymentResponse {
    private Long id;
    private Long emiId;
    private String userName;
    private Double paidAmount;
    private LocalDateTime paidAt;
}
