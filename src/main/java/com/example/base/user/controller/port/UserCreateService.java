package com.example.base.user.controller.port;


import com.example.base.user.domain.User;
import com.example.base.user.domain.UserCreate;

public interface UserCreateService {
    User create(UserCreate userCreate);
}
