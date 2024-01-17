package com.example.base.user.service;

import com.example.base.user.domain.exception.ResourceNotFoundException;
import com.example.base.common.service.port.ClockHolder;
import com.example.base.common.service.port.UuidHolder;
import com.example.base.user.controller.port.AuthenticationService;
import com.example.base.user.controller.port.UserCreateService;
import com.example.base.user.controller.port.UserReadService;
import com.example.base.user.controller.port.UserUpdateService;
import com.example.base.user.domain.User;
import com.example.base.user.domain.UserCreate;
import com.example.base.user.domain.UserStatus;
import com.example.base.user.domain.UserUpdate;
import com.example.base.user.service.port.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Builder
@RequiredArgsConstructor
public class UserServiceImpl implements UserCreateService, UserReadService, UserUpdateService, AuthenticationService {

    private final UserRepository userRepository;
    private final CertificationService certificationService;
    private final UuidHolder uuidHolder;
    private final ClockHolder clockHolder;
    private static final String RESOURCE_NAME = "Users";

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmailAndStatus(email, UserStatus.ACTIVE)
            .orElseThrow(() -> new ResourceNotFoundException("Email", email));
    }

    @Override
    public User getById(long id) {
        return userRepository.findByIdAndStatus(id, UserStatus.ACTIVE)
            .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, id));
    }


    @Override
    public User create(UserCreate userCreate) {
        User user = User.from(userCreate, uuidHolder);
        user = userRepository.save(user);
        certificationService.send(userCreate.email(), user.certificationCode(), user.id());
        return user;
    }

    @Override
    public User update(long id, UserUpdate userUpdate) {
        User user = getById(id);
        return userRepository.save(user.update(userUpdate, clockHolder));
    }

    @Override
    public User login(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, id));
        return userRepository.save(user.login(clockHolder));
    }

    @Override
    public void verifyEmail(long id, String certificationCode) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME, id));
        user = user.certificate(certificationCode, clockHolder);
        userRepository.save(user);
    }

}