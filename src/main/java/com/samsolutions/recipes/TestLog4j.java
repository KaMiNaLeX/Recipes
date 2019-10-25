package com.samsolutions.recipes;

import lombok.extern.log4j.Log4j2;
import org.apache.log4j.Logger;

/**
 * @author kaminskiy.alexey
 * @since 2019.10
 */
@Log4j2
public class TestLog4j {

   // private static final Logger LOGGER = Logger.getLogger(TestLog4j.class);

    public void doSomething() {
        //LOGGER.info("Info message");
        log.info("Hello from @Log4j2!");
    }
}
