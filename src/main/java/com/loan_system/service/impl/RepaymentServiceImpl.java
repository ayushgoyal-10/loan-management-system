package com.loan_system.service.impl;

import com.loan_system.dto.response.RepaymentResponse;
import com.loan_system.entity.EmiSchedule;
import com.loan_system.entity.EmiStatus;
import com.loan_system.entity.Repayment;
import com.loan_system.entity.User;
import com.loan_system.repository.EmiScheduleRepository;
import com.loan_system.repository.RepaymentRepository;
import com.loan_system.repository.UserRepository;
import com.loan_system.service.RepaymentService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RepaymentServiceImpl implements RepaymentService {

    private final UserRepository userRepository;
    private final EmiScheduleRepository emiScheduleRepository;
    private final RepaymentRepository repaymentRepository;

    public RepaymentServiceImpl(UserRepository userRepository, EmiScheduleRepository emiScheduleRepository, RepaymentRepository repaymentRepository) {
        this.userRepository = userRepository;
        this.emiScheduleRepository = emiScheduleRepository;
        this.repaymentRepository = repaymentRepository;
    }

    @Override
    public RepaymentResponse payEmi(Long emiId, Long userId) {

        User user= userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        EmiSchedule emi = emiScheduleRepository.findById(emiId)
                .orElseThrow(() -> new RuntimeException("EMI not found"));

        if(emi.getEmiStatus()== EmiStatus.PAID){
            throw new RuntimeException("EMI is already paid");
        }

        // if previous EMI is paid
        if (emi.getEmiNumber() > 1) {
            EmiSchedule previousEmi = emiScheduleRepository
                    .findByLoanAndEmiNumber(emi.getLoan(), emi.getEmiNumber() - 1)
                    .orElseThrow(() -> new RuntimeException("Previous EMI not found"));

            if (previousEmi.getEmiStatus() != EmiStatus.PAID) {
                throw new RuntimeException("Please pay EMI " + (emi.getEmiNumber() - 1) + " first");
            }
        }


        Double totalAmount= emi.getEmiAmount();
        if(LocalDate.now().isAfter(emi.getDueDate())){
            double penalty= emi.getEmiAmount() * 0.02; // 2% penalty
            penalty= Math.round(penalty*100.0) / 100.0;
            emi.setPenalty(penalty);
            totalAmount+= penalty;
        }

        Repayment repayment = Repayment.builder()
                .emiSchedule(emi)
                .user(user)
                .paidAmount(totalAmount)
                .paidAt(LocalDateTime.now())
                .build();

        Repayment savedRepayment = repaymentRepository.save(repayment);

        emi.setEmiStatus(EmiStatus.PAID);
        emiScheduleRepository.save(emi);

        return RepaymentResponse.builder()
                .id(savedRepayment.getId())
                .emiId(emi.getId())
                .userName(user.getName())
                .paidAmount(totalAmount)
                .paidAt(savedRepayment.getPaidAt())
                .build();
    }

    @Override
    public List<RepaymentResponse> getRepaymentsByUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Repayment> repayments = repaymentRepository.findByUser(user);
        return repayments.stream()
                .map(repayment -> RepaymentResponse.builder()
                        .id(repayment.getId())
                        .userName(repayment.getUser().getName())
                        .emiId(repayment.getEmiSchedule().getId())
                        .paidAmount(repayment.getPaidAmount())
                        .paidAt(repayment.getPaidAt())
                        .build()
                ).toList();
    }
}
