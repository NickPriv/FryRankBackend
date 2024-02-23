package com.fryrank.controller;
;

import org.junit.Assert;
import org.junit.Test;

import static com.fryrank.Constants.WELCOME_MESSAGE;

public class MessageControllerTests {

    MessageController controller = new MessageController();

    @Test
    public void testGetWelcomeMessage() throws Exception {
        String actualMessage = controller.getWelcomeMessage();
        String expectedMessage = WELCOME_MESSAGE;

        Assert.assertEquals(actualMessage, expectedMessage);
    }
}