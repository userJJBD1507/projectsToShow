package com.example.repeating;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @Autowired
    private Service service;
    @PostMapping("/create")
    public void create(@RequestBody Entity entity) throws Exception {
        service.implementation(entity);
    }
}
