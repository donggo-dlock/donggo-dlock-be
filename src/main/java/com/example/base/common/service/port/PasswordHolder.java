package com.example.base.common.service.port;

public interface PasswordHolder {
    public String encrypt(String password);
    public void match(String password, String encryptedPassword);
}
