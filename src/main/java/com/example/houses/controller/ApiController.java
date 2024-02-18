package com.example.houses.controller;

import jakarta.el.PropertyNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class ApiController {

    private Environment env;
    @GetMapping("/property")
    public ResponseEntity<String> getProperty(@RequestParam String name) {
        String property = env.getProperty(name);
        if (property == null) {
            throw new PropertyNotFoundException();
        }
        return ResponseEntity.ok(property);
    }
}
