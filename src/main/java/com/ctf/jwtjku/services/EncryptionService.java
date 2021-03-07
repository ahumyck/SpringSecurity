package com.ctf.jwtjku.services;

import lombok.SneakyThrows;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.security.SecureRandom;
import java.util.Base64;


public class EncryptionService {

    private final PasswordKeeper keeper;

    // AES-GCM parameters
    private static final int AES_KEY_SIZE = 128; //bits
    private static final int GCM_NONCE_LENGTH = 12; // bytes
    private static final int GCM_TAG_LENGTH = 16; // bytes

    public EncryptionService(PasswordKeeper keeper) {
        this.keeper = keeper;
    }

    @SneakyThrows
    private SecretKey getSecretKey(SecureRandom secureRandom) {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(AES_KEY_SIZE, secureRandom);
        return keyGen.generateKey();
    }

    private byte[] generateBytes(SecureRandom secureRandom, int length) {
        final byte[] bytes = new byte[length];
        secureRandom.nextBytes(bytes);
        return bytes;
    }

    private byte[] generateNonce(SecureRandom secureRandom) {
        return generateBytes(secureRandom, GCM_NONCE_LENGTH);
    }

    private byte[] generateAAD(SecureRandom secureRandom) {
        return generateBytes(secureRandom, 16);
    }

    private GCMParameterSpec generateSpec(SecureRandom secureRandom) {
        return new GCMParameterSpec(GCM_TAG_LENGTH * 8, generateNonce(secureRandom));
    }

    @SneakyThrows
    public String encrypt(String input) {
        SecureRandom random = new SecureRandom(keeper.getPassword(PasswordKeeper.ADMIN_PASS_KEY).getBytes());
        SecretKey secretKey = getSecretKey(random);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding", "SunJCE");
        GCMParameterSpec spec = generateSpec(random);

        cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec);
        cipher.updateAAD(generateAAD(random));
        return new String(Base64.getEncoder().encode(cipher.doFinal(input.getBytes())));
    }

    @SneakyThrows
    public String decrypt(String input) {
        SecureRandom random = new SecureRandom(keeper.getPassword(PasswordKeeper.ADMIN_PASS_KEY).getBytes());
        SecretKey secretKey = getSecretKey(random);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding", "SunJCE");
        GCMParameterSpec spec = generateSpec(random);

        cipher.init(Cipher.DECRYPT_MODE, secretKey, spec);
        cipher.updateAAD(generateAAD(random));
        return new String(cipher.doFinal(Base64.getDecoder().decode(input.getBytes())));
    }

}
