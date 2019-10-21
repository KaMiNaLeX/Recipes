package com.samsolutions.recipes;

import java.util.logging.Logger;

public class TestLog4j {
    private static final Logger logger = Logger.getLogger("APP2");

    public void doSomething() {
        System.out.print("Hello World");
        logger.info("doSomething");
    }
}
