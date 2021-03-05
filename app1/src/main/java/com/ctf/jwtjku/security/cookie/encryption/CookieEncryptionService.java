package com.ctf.jwtjku.security.cookie.encryption;

public interface CookieEncryptionService {

    String encrypt(String input);

    String decrypt(String input);

}
