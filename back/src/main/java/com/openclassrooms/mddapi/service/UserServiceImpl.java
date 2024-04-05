package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.UserResponseDTO;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * UserServiceImpl is a service class that implements IUserService.
 * It provides methods to interact with the UserRepository and perform operations on User entities.
 */
@AllArgsConstructor
@NoArgsConstructor
@Service
public class UserServiceImpl implements IUserService {
    private static final Logger log = LogManager.getLogger("UserServiceImpl");

    @Autowired
    private UserRepository userRepository;

    /**
     * Retrieves a User entity by its id.
     *
     * @param id the id of the User entity to retrieve
     * @return the User entity with the specified id
     */
    @Override
    public User getUserById(Long id) {
        log.info("get user by id");
        return userRepository.findById(id).get();
    }

    /**
     * Retrieves a User entity by its email.
     *
     * @param email the email of the User entity to retrieve
     * @return the User entity with the specified email
     */
    @Override
    public User getUserByEmail(String email) {
        log.info("get user by email");
        return userRepository.findByEmail(email).get();
    }

    /**
     * Retrieves all User entities.
     *
     * @return a list of all User entities
     */
    @Override
    public List<User> getAllUsers() {
        log.info("get all users");
        return userRepository.findAll();
    }

    /**
     * Modifies a User entity with the data from a UserResponseDTO.
     *
     * @param modifiedUser the UserResponseDTO containing the data to update the User entity with
     * @return the modified User entity
     */
    @Override
    public User modifyUser(UserResponseDTO modifiedUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = getUserByEmail(userDetails.getUsername());
        user.setName(modifiedUser.getName());
        user.setEmail(modifiedUser.getEmail());
        user.setUpdatedAt(LocalDateTime.now());
        log.info("modify user");
        return userRepository.save(user);
    }

}
