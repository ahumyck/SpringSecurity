package com.ctf.jwtjku.task;

import com.ctf.jwtjku.services.EncryptionService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@AllArgsConstructor
public class FlagEncryptor {

    private final EncryptionService encryptionService;

    /*
    TODO:
        1) Read flag from original file : Done
        2) Encrypt flag with com.ctf.jwtjku.services.EncryptionService
        3) Write encrypted content to "src/main/resources/encrypted_flag.txt"
        4) Delete original file "src/main/resources/flag.txt"
        -----
        Add this bean to AppConfigurator
     */
    public void encrypt() {

    }

    @SneakyThrows
    private String readOriginalFile(String filename) {
        Path path = Paths.get(filename);
        return Files.readAllLines(path).get(0);
    }

}
