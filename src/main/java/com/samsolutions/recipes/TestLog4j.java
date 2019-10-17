package com.samsolutions.recipes;

public class TestLog4j {
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(TestLog4j.class);

    public void doSomething() {
        System.out.print("Hello World");
        LOGGER.info("Info message");
    }
}
