package com.clinked.article_api.controllers;

import org.springframework.web.bind.annotation.RestController;
import com.clinked.article_api.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

}