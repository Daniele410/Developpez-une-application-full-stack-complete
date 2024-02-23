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

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class UserServiceImpl implements IUserService {
    private static final Logger log = LogManager.getLogger("UserServiceImpl");

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserById(Long id) {
        log.info("get user by id");
        return userRepository.findById(id).get();
    }

    @Override
    public User getUserByEmail(String email) {
        log.info("get user by email");
        return userRepository.findByEmail(email).get();
    }

    @Override
    public List<User> getAllUsers() {
        log.info("get all users");
        return userRepository.findAll();
    }

    @Override
    public User modifyUser(UserResponseDTO modifiedUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = getUserByEmail(userDetails.getUsername());
        user.setName(modifiedUser.getName());
        user.setEmail(modifiedUser.getEmail());
        log.info("modify user");
        return userRepository.save(user);
    }



}
