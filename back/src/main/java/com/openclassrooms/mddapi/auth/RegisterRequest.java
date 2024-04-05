package com.openclassrooms.mddapi.auth;

import com.openclassrooms.mddapi.util.Password;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RegisterRequest is a data transfer object (DTO) that encapsulates the user's registration details.
 * It is used during the registration process.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    /**
     * The name of the user trying to register.
     */
    private String name;

    /**
     * The email of the user trying to register.
     */
    private String email;

    /**
     * The password of the user trying to register.
     */
    @Password
    private String password;

    /**
     * The creation date of the user's account.
     */
    private String createdAt;

    /**
     * The last update date of the user's account.
     */
    private String updatedAt;
}