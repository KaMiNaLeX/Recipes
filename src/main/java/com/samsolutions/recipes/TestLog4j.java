package com.samsolutions.recipes;

import org.apache.log4j.Logger;

public class TestLog4j {

    private static final Logger LOGGER = Logger.getLogger(TestLog4j.class);

    public void doSomething() {
        LOGGER.info("Info message");
    }
}
