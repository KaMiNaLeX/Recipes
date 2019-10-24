package com.samsolutions.recipes;

import org.apache.log4j.Logger;

/**
 * @author kaminskiy.alexey
 * @since 2019.10
 */

public class TestLog4j {

    private static final Logger LOGGER = Logger.getLogger(TestLog4j.class);

    public void doSomething() {
        LOGGER.info("Info message");
        LOGGER.info("Hello");
    }
}
