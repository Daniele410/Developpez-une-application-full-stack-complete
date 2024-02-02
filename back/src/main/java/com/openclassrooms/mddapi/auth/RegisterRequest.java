package com.openclassrooms.mddapi.auth;

import com.openclassrooms.mddapi.util.Password;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String name;
    private String email;
    @Password
    private String password;
    private String createdAt;
    private String updatedAt;
}
