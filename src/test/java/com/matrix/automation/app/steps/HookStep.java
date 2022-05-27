package com.matrix.automation.app.steps;

import com.matrix.automation.step.BaseStep;
import io.cucumber.java.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HookStep extends BaseStep {
    protected static final Logger LOGGER = LoggerFactory.getLogger(HookStep.class);

    @Before
    public void initializeTest(Scenario scenario) {
        LOGGER.info("------------------------------------------------------------------");
        LOGGER.info("InitializeTest, scenarioName: {}", scenario.getName());
        super.initializeTest(scenario);
    }

    @After
    public void tearDownTest(Scenario scenario) {
        LOGGER.info("TearDownTest, scenarioName: {}", scenario.getName());
        super.tearDownTest(scenario);
    }

}
