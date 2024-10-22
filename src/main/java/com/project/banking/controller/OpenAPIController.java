package com.project.banking.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class OpenAPIController {
    @GetMapping(value = "/openapi", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getOpenAPI() throws IOException {
        Path path = Paths.get(new ClassPathResource("/static/OpenAPI.json").getURI());
        String content = Files.readString(path);
        return ResponseEntity.status(HttpStatus.OK).body(content);
    }
}
