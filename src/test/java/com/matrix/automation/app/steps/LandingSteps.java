package com.matrix.automation.app.steps;

import com.matrix.automation.factory.AppiumDriverBuilder;
import com.matrix.automation.screen.LandingScreen;
import com.matrix.automation.step.BaseStep;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class LandingSteps extends BaseStep {
    protected static final Logger LOGGER = LoggerFactory.getLogger(LandingSteps.class);

    @Given("^I launch Matrix app as an unregistered user$")
    public void iLaunchMatrixAppAsAnUnregisteredUser() throws InterruptedException {
        LOGGER.info("==>>> iLaunchMatrixAppAsAnUnregisteredUser, test1: {}", test);
        if (test==null) {
            try {
                //test = AppiumDriverBuilder.runWithEmulator(scenario);
                String runWith = System.getProperty("runWith");
                if (runWith == null) {
                    runWith = "emulator";
                }
                LOGGER.info("===>>> runWith: " + runWith);
                if (runWith.equals("emulator")) {
                    test = AppiumDriverBuilder.runWithEmulator(scenario);
                }
                if (runWith.equals("device")) {
                    test = AppiumDriverBuilder.runWithDevice(scenario);
                }
            } catch (Exception e) {
                LOGGER.info("Error al abrir la aplicación : " + e.getMessage());
                e.printStackTrace();
            }
        }
        LOGGER.info("==>>> iLaunchMatrixAppAsAnUnregisteredUser, test2: {}", test);
        LandingScreen screen = test.getScreen(LandingScreen.class);
        String stepName = screen.getStepName();
        LOGGER.info("==>>> iLaunchMatrixAppAsAnUnregisteredUser, stepName: {}", stepName);
    }

    @Then("^I see Landing screen$")
    public void iSeeLandingScreen() throws InterruptedException, IOException {
        LOGGER.info("==>>> iSeeLandingScreen, test1: {}", test);
        LandingScreen screen = test.getScreen(LandingScreen.class);
        String stepName = screen.getStepName();
        LOGGER.info("==>>> iSeeLandingScreen, stepName: {}", stepName);
        assertTrue(screen.isLandingScreenPresent());
    }

    @When("^I click on \"([^\"]*)\" button$")
    public void iClickOnButton(String buttonText) throws Throwable {
        LOGGER.info("==>>> iClickOnButton, buttonText: {}", buttonText);
        if (buttonText.equals("Obtener tu tarjeta")) {
            // Sign Up process
            LandingScreen screen = test.getScreen(LandingScreen.class);
            assertTrue(screen.clickOnObtainYouCardBtn());
        } else {
            // Login process
        }
    }
}