package com.matrix.automation.step;

import com.matrix.automation.factory.AppiumDriverBuilder;
import com.matrix.automation.factory.BaseTest;
import io.cucumber.java.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseStep {
    protected static final Logger LOGGER = LoggerFactory.getLogger(BaseStep.class);
    private AppiumDriverBuilder builder;
    protected static BaseTest test;
    protected Scenario scenario;

    protected void initializeTest(Scenario scenario) {
        try {
            this.scenario = scenario;
            if (builder == null) {
                builder = new AppiumDriverBuilder();
                String runWith = System.getProperty("runWith");
                if (runWith == null | runWith.equals("")) {
                    runWith = "emulator";
                }
                LOGGER.info("===>>> runWith: " + runWith);
                if (runWith.equals("emulator")) {
                    test = builder.runWithEmulator(scenario);
                }
                if (runWith.equals("device")) {
                    test = builder.runWithDevice(scenario);
                }
                test.setAppiumDriverBuilder(builder);
                builder.enableEvidenceCollector(scenario.getName());
            }
        } catch (Exception e) {
            LOGGER.info("Error al abrir la aplicación xxxxxxxxxxxxxx: {}", e.getMessage());
            LOGGER.error(String.valueOf(e));
        }
    }

    protected void tearDownTest(Scenario scenario) {
        if (test != null) {
            try {
                test.getDriver().quit();
            } catch (Exception e) {
                LOGGER.info("Error al terminar el test: {}", e.getMessage());
                LOGGER.error(String.valueOf(e));
                if (test.getDriver()!=null)
                    test.getDriver().quit();
            }
        }
    }
}
