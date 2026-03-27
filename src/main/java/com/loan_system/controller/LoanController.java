package com.loan_system.controller;

import com.loan_system.dto.request.LoanRequest;
import com.loan_system.dto.response.LoanResponse;
import com.loan_system.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/apply/{userId}")
    public ResponseEntity<LoanResponse> applyLoan(
            @PathVariable Long userId,
            @Valid @RequestBody LoanRequest request) {
        LoanResponse loanResponse = loanService.applyLoan(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(loanResponse);
    }

    @GetMapping
    public ResponseEntity<List<LoanResponse>> getLoansByUser(@PathVariable Long userId) {
        List<LoanResponse> loanResponseList = loanService.getLoanByUser(userId);
        return ResponseEntity.ok(loanResponseList);
    }
}
