package com.loan_system.service;

import com.loan_system.dto.request.RegisterRequest;
import com.loan_system.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse registerUser(RegisterRequest request);
    List<UserResponse> getAllUsers();
}
