package com.loan_system.service.impl;

import com.loan_system.dto.request.RegisterRequest;
import com.loan_system.dto.response.UserResponse;
import com.loan_system.entity.Role;
import com.loan_system.entity.User;
import com.loan_system.exception.BadRequestException;
import com.loan_system.repository.UserRepository;
import com.loan_system.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserResponse registerUser(RegisterRequest request) {

        // checking duplicate emails
        if(userRepository.existsByEmail(request.getEmail())){
            throw new BadRequestException("Email already exists");
        }

        // checking duplicate phone numbers
        if(userRepository.existsByPhoneNumber(request.getPhoneNumber())){
            throw new BadRequestException("Phone number already exists");
        }

        // mapping request dto to entity
        User user = modelMapper.map(request, User.class);
        user.setRole(Role.CUSTOMER); // default role is customer

        User saved = userRepository.save(user);
        return modelMapper.map(saved, UserResponse.class);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user-> modelMapper.map(user, UserResponse.class))
                .toList();
    }
}
