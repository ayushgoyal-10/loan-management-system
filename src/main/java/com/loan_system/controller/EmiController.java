package com.loan_system.controller;

import com.loan_system.dto.response.EmiResponse;
import com.loan_system.service.EmiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/emis")
public class EmiController {

    private final EmiService emiService;

    public EmiController(EmiService emiService) {
        this.emiService = emiService;
    }

    @GetMapping("/loan/{loanId}")
    public ResponseEntity<List<EmiResponse>> getEmisByLoan(
        @PathVariable Long loanId){

        List<EmiResponse> emisByLoan = emiService.getEmisByLoan(loanId);
        return ResponseEntity.ok(emisByLoan);
    }
}
