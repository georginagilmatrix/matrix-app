package com.matrix.automation.screen;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class SignUpScreen extends AbstractScreen {
    protected static final Logger LOGGER = LoggerFactory.getLogger(SignUpScreen.class);

    @iOSFindBy(xpath="//android.widget.TextView[@package='com.matrix.release'][contains(@text,'The CodePushMatrix 20')]")
    @AndroidFindBy(xpath="//android.widget.TextView[@package='com.matrix.release'][contains(@text,'The CodePushMatrix 20')]")
    private MobileElement signUpText;

    @iOSFindBy(xpath="//android.widget.TextView[@package='com.matrix.release'][contains(@text,'Click me!)]")
    @AndroidFindBy(xpath="//android.widget.TextView[@package='com.matrix.release'][contains(@text,'Click me!')]")
    private MobileElement clickMeBtnText;

    @iOSFindBy(xpath="//android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.Button")
    @AndroidFindBy(xpath="//android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.Button")
    private MobileElement clickMeBtn;


    public SignUpScreen(AppiumDriver driver) {
        super(driver);
    }

    public boolean isSignUpScreenPresent() throws InterruptedException, IOException {
        LOGGER.info("==>>> isSignUpScreenPresent 1");
        waitForTime(1000);
        if (signUpText != null) {
            LOGGER.info("==>>> isSignUpScreenPresent 2");
            wait.until(ExpectedConditions.visibilityOf(signUpText));
            LOGGER.info("==>>> isSignUpScreenPresent 3");
        }
        LOGGER.info("==>>> isSignUpScreenPresent 4");
        takeScreenshot();
        return true;
    }

    public boolean isClickMeBtnTextPresent() {
        LOGGER.info("==>>> isClickMeBtnTextPresent");
        //wait.until(ExpectedConditions.visibilityOf(obtainYouCardBtnText));
        ////takeScreenshot();
        //return true;
        return clickMeBtnText != null ? true : false;
    }

    public boolean clickOnClickMeBtn() throws IOException {
        LOGGER.info("==>>> clickOnClickMeBtn 1");
        if (isClickMeBtnTextPresent()) {
            LOGGER.info("==>>> clickOnClickMeBtn 2");
            clickMeBtn.click();
            LOGGER.info("==>>> clickOnClickMeBtn 3 click()");
            takeScreenshot();
            LOGGER.info("==>>> clickOnClickMeBtn 4 takeScreenshot()");
            return true;
        }
        return false;
    }

}
