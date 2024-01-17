package com.example.base.user.controller;

import com.example.base.user.controller.port.AuthenticationService;
import com.example.base.user.controller.port.UserCreateService;
import com.example.base.user.controller.port.UserReadService;
import com.example.base.user.controller.port.UserUpdateService;
import com.example.base.user.controller.response.MyProfileResponse;
import com.example.base.user.controller.response.UserResponse;
import com.example.base.user.domain.User;
import com.example.base.user.domain.UserCreate;
import com.example.base.user.domain.UserUpdate;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Builder
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserReadService userReadService;
    private final AuthenticationService authenticationService;
    private final UserUpdateService userUpdateService;
    private final UserCreateService userCreateService;

    @PostMapping
    public void create(@RequestBody UserCreate userCreate) {
        userCreateService.create(userCreate);
    }

    @ResponseStatus
    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable long id) {
        return UserResponse.from(userReadService.getById(id));
    }

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/{id}/verify")
    public void verifyEmail(
        @PathVariable long id,
        @RequestParam String certificationCode) {
        authenticationService.verifyEmail(id, certificationCode);
    }


    @GetMapping("/me")
    public MyProfileResponse getMyInfo(
        @RequestHeader("EMAIL") String email
    ) {
        User user = userReadService.getByEmail(email);
        user = authenticationService.login(user.id());
        return MyProfileResponse.from(user);
    }

    @PutMapping("/me")
    public void updateMyInfo(
        @RequestHeader("EMAIL") String email,
        @RequestBody UserUpdate userUpdate
    ) {
        User user = userReadService.getByEmail(email);
        userUpdateService.update(user.id(), userUpdate);
    }

}