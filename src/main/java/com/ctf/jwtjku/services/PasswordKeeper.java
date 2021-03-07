package com.ctf.jwtjku.services;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PasswordKeeper {

    public static final String ADMIN_PASS_KEY = "admin_password";
    private final Map<String, String> passwordKeeper = new ConcurrentHashMap<>();

    public void storePassword(String key, String password) {
        passwordKeeper.put(key, password);
    }

    public String getPassword(String key) {
        return passwordKeeper.get(key);
    }

}
