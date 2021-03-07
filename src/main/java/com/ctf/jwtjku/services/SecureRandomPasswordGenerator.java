package com.ctf.jwtjku.services;

import org.apache.tomcat.util.codec.binary.Base64;

import java.security.SecureRandom;

public class SecureRandomPasswordGenerator {

    private final SecureRandom random;

    public SecureRandomPasswordGenerator() {
        random = new SecureRandom();
    }

    public SecureRandomPasswordGenerator(byte[] seed) {
        random = new SecureRandom(seed);
    }

    public byte[] generatePasswordAndGetAsBytes(int passwordLength) {
        byte[] bytes = new byte[passwordLength];
        random.nextBytes(bytes);
        return bytes;
    }

    public String generatePasswordAndGetRawString(int passwordLength) {
        return new String(generatePasswordAndGetAsBytes(passwordLength));
    }


    public String generatePasswordAndGetAsBase64(int passwordLength) {
        return Base64.encodeBase64String(generatePasswordAndGetAsBytes(passwordLength));
    }

}
