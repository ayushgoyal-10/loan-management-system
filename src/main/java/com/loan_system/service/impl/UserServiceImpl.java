package com.loan_system.service.impl;

import com.loan_system.dto.request.LoginRequest;
import com.loan_system.dto.request.RegisterRequest;
import com.loan_system.dto.response.LoginResponse;
import com.loan_system.dto.response.UserResponse;
import com.loan_system.entity.Role;
import com.loan_system.entity.User;
import com.loan_system.exception.BadRequestException;
import com.loan_system.exception.ResourceNotFoundException;
import com.loan_system.repository.UserRepository;
import com.loan_system.security.JwtService;
import com.loan_system.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, BCryptPasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
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
        user.setPassword(passwordEncoder.encode(request.getPassword()));


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

    @Override
    public LoginResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        String token = jwtService.generateToken(user.getEmail());
        LoginResponse response = LoginResponse.builder()
                .token(token)
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();

        return response;
    }
}
