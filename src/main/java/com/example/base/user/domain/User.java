package com.example.base.user.domain;


import com.example.base.user.domain.exception.CertificationCodeNotMatchedException;
import com.example.base.common.service.port.ClockHolder;
import com.example.base.common.service.port.UuidHolder;
import lombok.Builder;

@Builder
public record User(Long id,
                   String email,
                   String nickname,
                   String address,
                   String certificationCode,
                   UserStatus status,
                   Long lastLoginAt) {
    public static User from(UserCreate userCreate, UuidHolder uuidHolder) {
        return User.builder()
                .email(userCreate.email())
                .nickname(userCreate.nickname())
                .certificationCode(uuidHolder.random())
                .address(userCreate.address())
                .status(UserStatus.PENDING)
                .build();
    }

    public User update(UserUpdate userUpdate, ClockHolder clockHolder) {
        return User.builder()
                .id(this.id)
                .email(this.email)
                .nickname(userUpdate.nickname())
                .address(userUpdate.address())
                .certificationCode(this.certificationCode)
                .status(this.status)
                .lastLoginAt(clockHolder.millis())
                .build();
    }

    public User login(ClockHolder clockHolder) {
        return User.builder()
                .id(this.id)
                .email(this.email)
                .nickname(this.nickname)
                .address(this.address)
                .certificationCode(this.certificationCode)
                .status(this.status)
                .lastLoginAt(clockHolder.millis())
                .build();
    }

    public User certificate(String code, ClockHolder clockHolder){
        if (!code.equals(this.certificationCode)) {
            throw new CertificationCodeNotMatchedException();
        }

        return User.builder()
                .id(this.id)
                .email(this.email)
                .nickname(this.nickname)
                .address(this.address)
                .certificationCode(this.certificationCode)
                .status(UserStatus.ACTIVE)
                .lastLoginAt(clockHolder.millis())
                .build();
    }
}
