package com.openclassrooms.mddapi.service;


import com.openclassrooms.mddapi.model.User;

import java.util.List;

public interface IUserService {

    User getUserById(Long id);

    User getUserByEmail(String email);

    List<User> getAllUsers();
}
