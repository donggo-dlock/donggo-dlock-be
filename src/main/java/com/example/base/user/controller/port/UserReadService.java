package com.example.base.user.controller.port;


import com.example.base.user.domain.User;

public interface UserReadService {
    User getById(long id);
    User getByEmail(String email);
}
