package com.example.securingweb.security.jwt;

public interface JsonWebTokenService {

    JsonWebToken generateToken(String username);

    String validateTokenAndGetUsername(String javaWebToken);
}
