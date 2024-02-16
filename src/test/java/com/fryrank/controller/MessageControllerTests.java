package com.fryrank.controller;
;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.Assert;

import static com.fryrank.Constants.WELCOME_MESSAGE;

public class MessageControllerTests {

    @Autowired
    private MessageController controller;

    @Test
    public void testGetWelcomeMessage() throws Exception {
        MessageController controller = new MessageController();

        String actualMessage = controller.getWelcomeMessage();
        String expectedMessage = WELCOME_MESSAGE;

        Assert.assertEquals(actualMessage, expectedMessage);
    }
}