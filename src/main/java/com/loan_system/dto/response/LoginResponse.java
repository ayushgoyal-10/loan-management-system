package com.loan_system.dto.response;

import com.loan_system.entity.Role;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {
    private String token;
    private String name;
    private String email;
    private Role role;

}
