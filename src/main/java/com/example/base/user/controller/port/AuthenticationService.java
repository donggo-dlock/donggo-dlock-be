package com.example.base.user.controller.port;


import com.example.base.user.domain.User;

public interface AuthenticationService {
    User login(long id);
    void verifyEmail(long id, String certificationCode);
}
