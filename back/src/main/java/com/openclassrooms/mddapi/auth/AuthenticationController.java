package com.openclassrooms.mddapi.auth;


import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;

@CrossOrigin(origins = "*")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) throws Exception {

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
