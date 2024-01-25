package com.fryrank.controller;

import com.fryrank.Constants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.fryrank.Constants.BASE_URI;

@RestController
@RequestMapping(BASE_URI + "/welcome")
public class MessageController {

    @GetMapping
    public String getWelcomeMessage() {
        return Constants.WELCOME_MESSAGE;
    }
}
