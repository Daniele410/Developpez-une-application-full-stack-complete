package com.openclassrooms.mddapi.controller;


import com.openclassrooms.mddapi.auth.AuthenticationService;
import com.openclassrooms.mddapi.dto.UserResponseDTO;
import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
import com.openclassrooms.mddapi.exception.UserNotFoundException;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;

@CrossOrigin(origins = "*")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {

    private final IUserService userService;
    private final AuthenticationService authenticationService;

    @GetMapping("/auth/me")
    public ResponseEntity<UserResponseDTO> me() throws ResourceNotFoundException {

        String userEmail = authenticationService.getAuthenticatedUserEmail();
        if (userEmail == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        UserResponseDTO responseDTO = authenticationService.me(userEmail);
        return new ResponseEntity<>(responseDTO, OK);
    }

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
}
