package com.loan_system.controller.admin;

import com.loan_system.dto.request.LoanStatusRequest;
import com.loan_system.dto.response.LoanResponse;
import com.loan_system.dto.response.UserResponse;
import com.loan_system.service.LoanService;
import com.loan_system.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final LoanService loanService;
    private final UserService userService;


    public AdminController(LoanService loanService, UserService userService) {
        this.loanService = loanService;
        this.userService = userService;
    }

    @GetMapping("/loans/all")
    public ResponseEntity<Page<LoanResponse>> getAllLoans(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){

        Pageable pageable= PageRequest.of(page, size, Sort.by("appliedAt").descending());
        Page<LoanResponse> allLoans = loanService.getAllLoans(pageable);
        return ResponseEntity.ok(allLoans);
    }

    @PutMapping("/loans/{loanId}/status")
    public ResponseEntity<LoanResponse> updateLoanStatus(
            @PathVariable Long loanId,
            @RequestBody LoanStatusRequest request){

        LoanResponse loanResponse = loanService.updateLoanStatus(loanId, request);
        return ResponseEntity.ok(loanResponse);
    }


    @GetMapping("/users/all")
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        List<UserResponse> allUsers = userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }
}
