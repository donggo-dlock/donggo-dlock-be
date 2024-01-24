package com.example.base.reportable.domain;

import com.example.base.common.exception.PasswordNotMatchException;
import com.example.base.common.service.port.PasswordHolder;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Slf4j
@Component
public class SystemPasswordHolder implements PasswordHolder {

    @Value("${key-password}")
    private String base64Key;

    private SecretKey key;
    private static final String ALGORITHM = "AES";

    @PostConstruct
    void generateKey() {
        byte[] decodedKey = Base64.getDecoder().decode(base64Key);
        key = new SecretKeySpec(decodedKey, 0, decodedKey.length, ALGORITHM);
    }

    public String encrypt(String data){
        try{
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedBytes = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void match(String password, String encryptedPassword) {
        String decryptMsg = decrypt(encryptedPassword);
        if (!password.equals(decryptMsg)) {
            throw new PasswordNotMatchException();
        }
    }

    private String decrypt(String encryptedData){
        try{
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
            return new String(decryptedBytes);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
