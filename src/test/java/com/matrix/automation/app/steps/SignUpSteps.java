package com.matrix.automation.app.steps;

import com.matrix.automation.screen.SignUpScreen;
import com.matrix.automation.step.BaseStep;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class SignUpSteps extends BaseStep {
    protected static final Logger LOGGER = LoggerFactory.getLogger(SignUpSteps.class);

    @Then("^I see Sign Up screen$")
    public void iSeeSignUpScreen() throws InterruptedException, IOException {
        LOGGER.info("==>>> iSeeSignUpScreen, test1: {}", test);
        SignUpScreen screen = test.getScreen(SignUpScreen.class);
        String stepName = screen.getStepName();
        LOGGER.info("==>>> iSeeSignUpScreen, stepName: {}", stepName);
        assertTrue(screen.isSignUpScreenPresent());
    }

    @When("^I click on \"([^\"]*)\" button in Sign Up screen$")
    public void iClickOnButtonInSignUpScreen(String buttonText) throws Throwable {
        LOGGER.info("==>>> iClickOnButtonInSignUpScreen, buttonText: {}", buttonText);
        if (buttonText.equals("Click me!")) {
            SignUpScreen screen = test.getScreen(SignUpScreen.class);
            assertTrue(screen.clickOnClickMeBtn());
        }
    }

    @Then("^I start Onboarding process$")
    public void iStartOnboardingProcess() {
    }
}