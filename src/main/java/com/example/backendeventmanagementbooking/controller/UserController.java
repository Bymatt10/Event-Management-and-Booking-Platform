package com.example.backendeventmanagementbooking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Controller("api/v1/user/")
public class UserController {

    @GetMapping("")
    public String index() {
        return this.getClass().getSimpleName();
    }
}
