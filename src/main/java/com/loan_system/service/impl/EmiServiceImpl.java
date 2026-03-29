package com.loan_system.service.impl;

import com.loan_system.dto.response.EmiResponse;
import com.loan_system.entity.EmiSchedule;
import com.loan_system.entity.EmiStatus;
import com.loan_system.entity.LoanApplication;
import com.loan_system.exception.ResourceNotFoundException;
import com.loan_system.repository.EmiScheduleRepository;
import com.loan_system.repository.LoanApplicationRepository;
import com.loan_system.service.EmiService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmiServiceImpl implements EmiService {

    private final EmiScheduleRepository emiScheduleRepository;
    private final LoanApplicationRepository loanApplicationRepository;
    private final ModelMapper modelMapper;

    public EmiServiceImpl(EmiScheduleRepository emiScheduleRepository, LoanApplicationRepository loanApplicationRepository, ModelMapper modelMapper) {
        this.emiScheduleRepository = emiScheduleRepository;
        this.loanApplicationRepository = loanApplicationRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void generateEmiSchedule(LoanApplication loan) {

        // calculate EMI amount using formula
        double principal = loan.getAmount();
        int tenureMonths = loan.getTenureMonths();
        double annualInterestRate = 10.0; // 10% per year (fixed for now)
        double monthlyRate= annualInterestRate / (12 * 100);

        double emiAmount;
        // EMI formula : P * R * (1+R)^N / ((1+R)^N -1)
        if(monthlyRate==0){
            emiAmount= principal/tenureMonths;
        }else{
            double power= Math.pow(1+monthlyRate, tenureMonths);
            emiAmount= (principal*monthlyRate*power) / (power-1);
        }

        // round to 2 decimal places
        emiAmount= Math.round(emiAmount*100.0)/100.0;

        // generate EMI schedule
        List<EmiSchedule> emiList= new ArrayList<>();
        LocalDate dueDate= LocalDate.now().plusMonths(1); // first EMI due next month

        for(int i=1; i<=tenureMonths; i++){
            EmiSchedule emi = EmiSchedule.builder()
                    .loan(loan)
                    .emiNumber(i)
                    .emiAmount(emiAmount)
                    .dueDate(dueDate)
                    .emiStatus(EmiStatus.PENDING)
                    .penalty(0.0)
                    .build();

            emiList.add(emi);
            dueDate= dueDate.plusMonths(1); // next EMI due next month
        }

        // save all EMIs
        emiScheduleRepository.saveAll(  emiList);

    }

    @Override
    public List<EmiResponse> getEmisByLoan(Long loanId) {
        LoanApplication loan = loanApplicationRepository.findById(loanId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found"));

        List<EmiResponse> emiResponses = emiScheduleRepository.findByLoan(loan).stream()
                .map(emi -> modelMapper.map(emi, EmiResponse.class)).toList();
        return emiResponses;
    }
}
