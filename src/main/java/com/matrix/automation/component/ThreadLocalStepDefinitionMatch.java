package com.matrix.automation.component;

import cucumber.runtime.StepDefinitionMatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadLocalStepDefinitionMatch {
    protected static final Logger LOGGER = LoggerFactory.getLogger(ThreadLocalStepDefinitionMatch.class);

    private static ThreadLocal<StepDefinitionMatch> threadStepDefMatch = new InheritableThreadLocal<StepDefinitionMatch>();

    private ThreadLocalStepDefinitionMatch() {
        LOGGER.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>> StepDefinitionMatch ThreadLocalStepDefinitionMatch");
    }

    public static StepDefinitionMatch get() {
        LOGGER.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> StepDefinitionMatch get");
        if (threadStepDefMatch == null) {
            threadStepDefMatch = new InheritableThreadLocal<StepDefinitionMatch>();
            LOGGER.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> StepDefinitionMatch get, threadStepDefMatch1: {}", threadStepDefMatch);
        }
        LOGGER.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> StepDefinitionMatch get, threadStepDefMatch2: {}",  threadStepDefMatch);
        //return threadStepDefMatch.get();
        StepDefinitionMatch match = threadStepDefMatch.get();
        LOGGER.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> StepDefinitionMatch get, match: {}", match);
        return match;
    }

    public static void set(StepDefinitionMatch match) {
        threadStepDefMatch.set(match);
    }

    public static void remove() {
        threadStepDefMatch.remove();
    }

}
