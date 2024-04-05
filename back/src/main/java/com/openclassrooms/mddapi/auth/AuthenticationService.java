package com.openclassrooms.mddapi.auth;

import com.openclassrooms.mddapi.configuration.JwtService;
import com.openclassrooms.mddapi.dto.UserResponseDTO;
import com.openclassrooms.mddapi.exception.ResourceNotFoundException;
import com.openclassrooms.mddapi.model.Role;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.service.ITopicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * AuthenticationService is a service class that handles authentication requests.
 * It provides methods for user registration and authentication.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final ITopicService topicService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * AuthenticationService is a service class that handles authentication requests.
     * It provides methods for user registration and authentication.
     */
    public AuthenticationResponse register(RegisterRequest request) throws Exception {
        var user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .role(Role.USER)
                .build();
        if (userRepository.existsByEmail(user.getEmail())) {
            log.warn("email: " + user.getEmail() + " is present in Database");
            throw new ResourceNotFoundException("User with email " + user.getEmail() + " is present in Database");
        } else {
            userRepository.save(user);

            var jwtToken = jwtService.generateToken(user.getEmail());
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        }
    }

    /**
     * Authenticates a user.
     *
     * @param request the authentication request containing the user's email and password
     * @return an AuthenticationResponse containing the JWT token for the authenticated user
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user.getEmail());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    /**
     * Retrieves the details of the currently authenticated user.
     *
     * @param userEmail the email of the authenticated user
     * @return a UserResponseDTO containing the details of the authenticated user
     * @throws ResourceNotFoundException if a user with the specified email could not be found
     */
    public UserResponseDTO me(String userEmail) throws ResourceNotFoundException {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + userEmail + " not found"));
        List<Topic> topics = topicService.getUserSubscribedTopicsPart();
        UserResponseDTO response = new UserResponseDTO();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setTopic(topics);
        response.setCreated_at(user.getCreatedAt());
        response.setUpdated_at(user.getUpdatedAt());

        return response;
    }

    /**
     * Retrieves the email of the currently authenticated user.
     *
     * @return the email of the currently authenticated user, or null if no user is authenticated
     */
    public String getAuthenticatedUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return authentication.getName();
        }
    }
}