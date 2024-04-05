package com.openclassrooms.mddapi.controller;


import com.openclassrooms.mddapi.auth.AuthenticationService;
import com.openclassrooms.mddapi.dto.UserResponseDTO;
import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
import com.openclassrooms.mddapi.exception.UserNotFoundException;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;

/**
 * UserController is a REST controller that handles HTTP requests related to users.
 * It uses IUserService and AuthenticationService to perform operations on users.
 */
@CrossOrigin(origins = "*")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {

    private final IUserService userService;
    private final AuthenticationService authenticationService;

    /**
     * Retrieves the authenticated user.
     *
     * @return a ResponseEntity containing the UserResponseDTO of the authenticated user
     * @throws ResourceNotFoundException if the authenticated user is not found
     */
    @GetMapping("/auth/me")
    public ResponseEntity<UserResponseDTO> me() throws ResourceNotFoundException {

        String userEmail = authenticationService.getAuthenticatedUserEmail();
        if (userEmail == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        UserResponseDTO responseDTO = authenticationService.me(userEmail);
        return new ResponseEntity<>(responseDTO, OK);
    }

    /**
     * Retrieves a user by its ID.
     *
     * @param id the ID of the user
     * @return a ResponseEntity containing the UserResponseDTO
     * @throws UserNotFoundException if the user is not found
     */
    @GetMapping("/user/{id}")
    public ResponseEntity<UserResponseDTO> retrieveUserById(@PathVariable Long id) throws UserNotFoundException {
        Optional<User> user = Optional.ofNullable(userService.getUserById(id));
        if (user.isEmpty()) {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
        UserResponseDTO meResponse =
                new UserResponseDTO(user.get());
        return ResponseEntity.ok(meResponse);
    }

    /**
     * Modifies a user.
     *
     * @param modifiedUser the user modified email and name
     * @return response entity with the modified user
     */
    @PutMapping("/user/me")
    public ResponseEntity<UserResponseDTO> modifyUser(@RequestBody @Valid UserResponseDTO modifiedUser) {
        User user = userService.modifyUser(modifiedUser);
        UserResponseDTO responseDTO = new UserResponseDTO(user);
        return ResponseEntity.ok(responseDTO);
    }

}
