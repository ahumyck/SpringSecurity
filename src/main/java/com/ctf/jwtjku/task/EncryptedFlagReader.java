package com.ctf.jwtjku.task;

import com.ctf.jwtjku.services.EncryptionService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EncryptedFlagReader {


    private final EncryptionService encryptionService;

    /*
    TODO:
        1) Read encrypted flag from file "src/main/resources/encrypted_flag.txt"
        2) Decrypt flag with com.ctf.jwtjku.services.EncryptionService
        3) When admin says hi return flag
        -----
        Add this bean to AppConfigurator
        Add this service to TestSecurityController
     */
    public String readFlag() {
        return "";
    }
}
