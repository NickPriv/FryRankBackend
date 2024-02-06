package com.fryrank.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static  com.fryrank.Constants.API_PATH;
import static  com.fryrank.Constants.WELCOME_MESSAGE;

@RestController
public class MessageController {

    @GetMapping(API_PATH + "/welcome")
    public String getWelcomeMessage() {
        return WELCOME_MESSAGE;
    }
}
