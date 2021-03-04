package com.example.securingweb.controllers;


import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;


@RestController
public class JkuController {

    //todo just for test, need impl in future
    @GetMapping(value = "/secret")
    public String secret() {
        return Base64.encode("javamaster".getBytes(StandardCharsets.UTF_8));
    }

    //todo just for test, need remove in future
    @GetMapping(value = "/vzlomjopi")
    public String vzlom() {
        return Base64.encode("secret".getBytes(StandardCharsets.UTF_8)); //c2VjcmV0
    }
}
