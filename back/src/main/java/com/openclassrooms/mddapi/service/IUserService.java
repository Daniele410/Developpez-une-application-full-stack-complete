package com.openclassrooms.mddapi.service;


import com.openclassrooms.mddapi.model.User;

public interface IUserService {

    User getUserById(Long id);
}
