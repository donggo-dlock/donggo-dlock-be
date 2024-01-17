package com.example.base.user.infrastructure;


import com.example.base.user.domain.exception.ResourceNotFoundException;
import com.example.base.user.domain.User;
import com.example.base.user.domain.UserStatus;
import com.example.base.user.service.port.UserRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class FakeUserRepository implements UserRepository {
    private final AtomicLong idGenerator = new AtomicLong(1L);
    private final List<User> data = Collections.synchronizedList(new ArrayList<>());


    @Override
    public User getById(long id) {
        return data.stream()
                .filter(item -> item.id().equals(id))
                .findAny()
                .orElseThrow(() -> new ResourceNotFoundException("Users", id));
    }

    @Override
    public Optional<User> findByIdAndStatus(long id, UserStatus userStatus) {
        return data.stream()
                .filter(item -> item.id().equals(id) && item.status().equals(userStatus))
                .findAny();
    }

    @Override
    public Optional<User> findByEmailAndStatus(String email, UserStatus userStatus) {
        return data.stream()
                .filter(item -> item.email().equals(email) && item.status().equals(userStatus))
                .findAny();
    }

    @Override
    public User save(User user) {
        if (user.id() == null || user.id() == 0) {
            User newUser = User.builder()
                    .id(idGenerator.getAndIncrement())
                    .email(user.email())
                    .nickname(user.nickname())
                    .email(user.email())
                    .address(user.address())
                    .status(user.status())
                    .certificationCode(user.certificationCode())
                    .lastLoginAt(user.lastLoginAt())
                    .build();
            data.add(newUser);
            return newUser;
        }
        data.removeIf(item -> item.id().equals(user.id()));
        data.add(user);
        return user;
    }

    @Override
    public Optional<User> findById(long id) {
        return data.stream()
                .filter(item -> item.id().equals(id))
                .findAny();
    }
}
