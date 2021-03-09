package com.ctf.jwtjku.services;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PasswordService {

    public static final String ADMIN_PASS_KEY = "admin_password";
    private final Map<String, String> passwordKeeper = new ConcurrentHashMap<>();

    private final SecureRandomPasswordGenerator passwordGenerator;

    public PasswordService(SecureRandomPasswordGenerator passwordGenerator) {
        this.passwordGenerator = passwordGenerator;
    }

    public String generatePassword(){
        return passwordGenerator.generatePasswordAndGetAsBase64(128);
    }

    public void storePassword(String key, String password) {
        passwordKeeper.put(key, password);
    }

    public String getPassword(String key) {
        return passwordKeeper.get(key);
    }

}
