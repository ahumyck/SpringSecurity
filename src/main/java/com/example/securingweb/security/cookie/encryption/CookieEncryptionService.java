package com.example.securingweb.security.cookie.encryption;

import java.security.NoSuchAlgorithmException;

public interface CookieEncryptionService {

    String encrypt(String input);

    String decrypt(String input);

}
