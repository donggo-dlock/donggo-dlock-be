package com.example.base.user.service.port;


import com.example.base.user.domain.User;
import com.example.base.user.domain.UserStatus;

import java.util.Optional;

public interface UserRepository {

    User getById(long id);

    Optional<User> findByIdAndStatus(long id, UserStatus userStatus);

    Optional<User> findByEmailAndStatus(String email, UserStatus userStatus);

    User save(User user);

    Optional<User> findById(long id);
}
