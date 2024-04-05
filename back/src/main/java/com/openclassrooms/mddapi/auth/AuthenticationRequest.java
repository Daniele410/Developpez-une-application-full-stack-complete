package com.openclassrooms.mddapi.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AuthenticationRequest is a data transfer object (DTO) that encapsulates the user's email and password.
 * It is used during the authentication process.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    /**
     * The email of the user trying to authenticate.
     */
    private String email;

    /**
     * The password of the user trying to authenticate.
     */
    private String password;
}
