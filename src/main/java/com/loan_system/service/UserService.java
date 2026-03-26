package com.loan_system.service;

import com.loan_system.dto.request.RegisterRequest;
import com.loan_system.dto.response.UserResponse;

public interface UserService {
    UserResponse registerUser(RegisterRequest request);
}
