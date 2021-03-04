package com.example.securingweb.security.jwt;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class JwtConverter {

    private final TypeReference<HashMap<String, Object>> typeRef;
    private final ObjectMapper mapper;

    public JwtConverter() {
        this(new TypeReference<HashMap<String, Object>>() {}, new ObjectMapper());
    }

    @SneakyThrows
    public Map<String, Object> convertResponse(String response) {
        return mapper.readValue(response, typeRef);
    }
}
