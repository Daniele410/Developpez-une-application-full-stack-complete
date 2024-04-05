package com.openclassrooms.mddapi.auth;

import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;

/**
 * AuthenticationController is a REST controller that handles authentication requests.
 * It provides endpoints for user registration and login.
 */
@CrossOrigin(origins = "*")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;

    /**
     * Handles the registration of a new user.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request) throws Exception {

        if (StringUtils.isAnyBlank(request.getName(), request.getEmail(), request.getPassword())) {
            log.error("Fields should not be empty!");
            return new ResponseEntity<>(new ErrorMessage("Fields should not be empty!"), HttpStatus.FORBIDDEN);
        }
        Optional<User> userEmail = userRepository.findByEmail(request.getEmail());
        if (userEmail.isPresent()) {
            log.error("Email already exists");
            return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
        }
        log.info("User registered successfully");
        return new ResponseEntity<>(authenticationService.register(request), OK);
    }

    /**
     * Handles the login of a user.
     *
     * @param request the login request containing the user's email and password
     * @return a ResponseEntity indicating the success or failure of the operation
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {
        Optional<User> userEmail = userRepository.findByEmail(request.getEmail());
        if (!userEmail.isPresent()) {
            if (request.getEmail() == null || request.getPassword() == null || request.getEmail().isEmpty() || request.getPassword().isEmpty()) {
                log.error("Fields should not be empty!");
                return new ResponseEntity<>(new ErrorMessage("Incorrect credentials or Fields should not be empty!"), HttpStatus.BAD_REQUEST);
            }
            log.error("Email Not present in Database");
            return new ResponseEntity<>("Email Not present in Database", HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(authenticationService.authenticate(request), OK);
    }

}
