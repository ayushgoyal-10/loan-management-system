package com.loan_system.controller;

import com.loan_system.dto.response.RepaymentResponse;
import com.loan_system.service.RepaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/repayments")
public class Repayment {

    private final RepaymentService repaymentService;

    public Repayment(RepaymentService repaymentService) {
        this.repaymentService = repaymentService;
    }

    @PostMapping("/pay/{emiId}/{userId}")
    public ResponseEntity<RepaymentResponse> payEmi(
            @PathVariable Long emiId,
            @PathVariable Long userId
    ){
        RepaymentResponse repaymentResponse = repaymentService.payEmi(emiId, userId);
        return ResponseEntity.ok(repaymentResponse);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RepaymentResponse>> getRepaymentsByUser(@PathVariable Long userId){
        List<RepaymentResponse> repaymentsByUser = repaymentService.getRepaymentsByUser(userId);
        return ResponseEntity.ok(repaymentsByUser);
    }
}
