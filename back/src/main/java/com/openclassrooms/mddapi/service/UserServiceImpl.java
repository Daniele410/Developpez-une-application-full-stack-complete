package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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



}
