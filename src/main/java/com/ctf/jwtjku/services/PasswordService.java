package com.ctf.jwtjku.services;

public class PasswordService {


    private final SecureRandomPasswordGenerator passwordGenerator;

    public PasswordService(SecureRandomPasswordGenerator passwordGenerator) {
        this.passwordGenerator = passwordGenerator;
    }

    public String generatePassword() {
        return passwordGenerator.generatePasswordAndGetAsBase64(128);
    }

}
