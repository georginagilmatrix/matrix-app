package com.matrix.automation.screen;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class LandingScreen extends AbstractScreen {
    protected static final Logger LOGGER = LoggerFactory.getLogger(LandingScreen.class);

    @iOSFindBy(id="com.matrix.release:id/action_bar_root")
    @AndroidFindBy(id="com.matrix.release:id/action_bar_root")
    private MobileElement logo1;

    @iOSFindBy(xpath="//android.widget.TextView[@package='com.matrix.release'][contains(@text,'Obtener tu tarjeta')]")
    @AndroidFindBy(xpath="//android.widget.TextView[@package='com.matrix.release'][contains(@text,'Obtener tu tarjeta')]")
    private MobileElement obtainYouCardBtnText;

    @iOSFindBy(xpath="//android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.Button[1]")
    @AndroidFindBy(xpath="//android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.widget.Button[1]")
    private MobileElement obtainYouCardBtn;

    public LandingScreen(AppiumDriver driver) {
        super(driver);
    }

    public boolean isLandingScreenPresent() throws InterruptedException {
        LOGGER.info("==>>> isLandingScreenPresent 1");
        //waitForTime(2000);
        //LOGGER.info("==>>> isLandingScreenPresent 2 " + logo.isDisplayed());

        //MobileElement logo =  findElement (MobileBy.id("com.matrix.release:id/action_bar_root"));
        //LOGGER.info("==>>> isLandingScreenPresent 2 " + logo.isDisplayed());

        //MobileElement logo =  findElement (MobileBy.id("action_bar_root"));

        //By by = By.xpath("//android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.Button");
        //MobileElement logo =  findElement (by);

        //wait.until(ExpectedConditions.visibilityOf(logo));
        if (obtainYouCardBtnText != null) {
            LOGGER.info("==>>> isLandingScreenPresent 2");
            wait.until(ExpectedConditions.visibilityOf(obtainYouCardBtnText));
            LOGGER.info("==>>> isLandingScreenPresent 3");
        }
        LOGGER.info("==>>> isLandingScreenPresent 4");
        //takeScreenshot();
        return true;
    }

    public boolean isObtainYouCardBtnTextPresent() {
        LOGGER.info("==>>> isObtainYouCardBtnTextPresent");
        //wait.until(ExpectedConditions.visibilityOf(obtainYouCardBtnText));
        ////takeScreenshot();
        //return true;
        return obtainYouCardBtnText != null ? true : false;
    }

    public boolean clickOnObtainYouCardBtn() throws IOException {
        LOGGER.info("==>>> clickOnObtainYouCardBtn 1");
        if (isObtainYouCardBtnTextPresent()) {
            LOGGER.info("==>>> clickOnObtainYouCardBtn 2");
            obtainYouCardBtn.click();
            LOGGER.info("==>>> clickOnObtainYouCardBtn 3 click()");
            takeScreenshot();
            LOGGER.info("==>>> clickOnObtainYouCardBtn 4 takeScreenshot()");
            return true;
        }
        return false;
    }

    //android.widget.TextView[@package="com.matrix.release"][contains(@text,"The CodePushMatrix 20")]
}
