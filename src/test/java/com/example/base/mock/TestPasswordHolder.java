package com.example.base.mock;

import com.example.base.common.exception.PasswordNotMatchException;
import com.example.base.common.service.port.PasswordHolder;

public class TestPasswordHolder implements PasswordHolder {
    @Override
    public String encrypt(String password) {
        return password;
    }

    @Override
    public void match(String password, String encryptedPassword) {
        if (password != encryptedPassword) throw new PasswordNotMatchException();
    }
}
