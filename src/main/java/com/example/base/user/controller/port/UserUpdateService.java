package com.example.base.user.controller.port;


import com.example.base.user.domain.User;
import com.example.base.user.domain.UserUpdate;

public interface UserUpdateService {
    User update(long id, UserUpdate userUpdate);
}
