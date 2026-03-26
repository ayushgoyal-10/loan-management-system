package com.loan_system.service.impl;

import com.loan_system.dto.request.LoanRequest;
import com.loan_system.dto.request.LoanStatusRequest;
import com.loan_system.dto.response.LoanResponse;
import com.loan_system.entity.LoanApplication;
import com.loan_system.entity.LoanStatus;
import com.loan_system.entity.User;
import com.loan_system.repository.LoanApplicationRepository;
import com.loan_system.repository.UserRepository;
import com.loan_system.service.LoanService;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;

public class LoanServiceImpl implements LoanService {

    private final LoanApplicationRepository loanApplicationRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final EmiService emiService;

    public LoanServiceImpl(LoanApplicationRepository loanApplicationRepository, UserRepository userRepository, ModelMapper modelMapper, EmiService emiService) {
        this.loanApplicationRepository = loanApplicationRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.emiService = emiService;
    }

    @Override
    public LoanResponse applyLoan(Long userId, LoanRequest request) {
        User user = userRepository.findByEmail(userId).orElseThrow(() -> new RuntimeException("User not found"));

        LoanApplication loan = LoanApplication.builder()
                .user(user)
                .amount(request.getAmount())
                .tenureMonths(request.getTenureMonths())
                .purpose(request.getPurpose())
                .status(LoanStatus.PENDING)
                .appliedAt(LocalDateTime.now())
                .build();

        LoanApplication saved = loanApplicationRepository.save(loan);
        LoanResponse response = modelMapper.map(saved, LoanResponse.class);
        response.setUserName(user.getName());
        return response;
    }

    @Override
    public LoanResponse updateLoanStatus(Long loanId, LoanStatusRequest request) {
        LoanApplication loan = loanApplicationRepository.findById(loanId).orElseThrow(() -> new RuntimeException("Loan application not found"));

        loan.setStatus(request.getStatus());
        loan.setUpdatedAt(LocalDateTime.now());

        if(loan.getStatus()==LoanStatus.REJECTED){
            loan.setRejectionReason(request.getRejectionReason());
        }

        if(loan.getStatus()==LoanStatus.APPROVED){
            emiService.generateEmiSchedule(loan);
        }

        LoanApplication saved = loanApplicationRepository.save(loan);
        LoanResponse response = modelMapper.map(loan, LoanResponse.class);
        response.setUserName(loan.getUser().getName());
        return response;
    }

    @Override
    public List<LoanResponse> getLoanByUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        List<LoanApplication> loans = loanApplicationRepository.findByUser(user);

        List<LoanResponse> loanResponses = loans.stream()
                .map(loan->{
                    LoanResponse response = modelMapper.map(loan, LoanResponse.class);
                    response.setUserName(loan.getUser().getName());
                    return response;
                }).toList();

        return loanResponses;
    }

    @Override
    public List<LoanResponse> getAllLoans() {
        List<LoanApplication> loans = loanApplicationRepository.findAll();
        List<LoanResponse> loanResponses = loans.stream()
                .map(loan -> {
                    LoanResponse response = modelMapper.map(loan, LoanResponse.class);
                    response.setUserName(loan.getUser().getName());
                    return response;
                }).toList();
        return loanResponses;
    }
}
