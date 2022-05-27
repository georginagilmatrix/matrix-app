package com.matrix.automation.screen;

import com.matrix.automation.component.ThreadLocalStepDefinitionMatch;
import com.matrix.automation.screen.listener.EvidenceScreenInterceptor;
import com.matrix.automation.screen.listener.ScreenInterceptor;
import cucumber.runtime.StepDefinitionMatch;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.HideKeyboardStrategy;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractScreen<SCREEN extends AbstractScreen<?>> {
    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractScreen.class);

    protected final AppiumDriver driver;
    protected WebDriverWait wait;
    protected Scenario scenario;

    private List<ScreenInterceptor> screenListeners = new ArrayList<>();
    private EvidenceScreenInterceptor evidenceScreenInterceptor;
    private static String step = "";
    private List<String> steps;
    private static int stepIndex = 0;
    private String currentMatch = "";

    public AbstractScreen(AppiumDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 5);
        String ctx = driver.getContext();
        LOGGER.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Found ctx: {}", ctx);
        PageFactory.initElements(new AppiumFieldDecorator(driver.context(ctx), Duration.ofSeconds(1)), this);
        LOGGER.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>> AbstractScreen");
    }
    
    
    public void addScreenInterceptor(ScreenInterceptor screenInterceptor) {
        this.screenListeners.add(screenInterceptor);
    }

    public void setEvidenceScreenInterceptor(EvidenceScreenInterceptor evidenceScreenInterceptor) {
        this.evidenceScreenInterceptor = evidenceScreenInterceptor;
    }

    public EvidenceScreenInterceptor getEvidenceScreenInterceptor() {
        return this.evidenceScreenInterceptor;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }

    public void resetStepName() {
        this.step = "";
    }

    public String getStepName() {
        LOGGER.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> AbstractScreen, getStepName");
        StepDefinitionMatch match = ThreadLocalStepDefinitionMatch.get();
        LOGGER.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> AbstractScreen, getStepName match: {}", match);
        if (match != null) {
            LOGGER.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>> match.getStepName() y: {}", match.getStepName());
            LOGGER.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> match.getStepName() z: {}", this.step);
            return match.getStepName();
        }
        return "";
    }

    public void hideKeyboard() {
        if (isAndroid()) {
            driver.hideKeyboard();
        } else {
            IOSDriver iosDriver = (IOSDriver) driver;
            iosDriver.hideKeyboard(HideKeyboardStrategy.PRESS_KEY, "Done");
        }
    }

    public boolean isAndroid() {
        return driver instanceof AndroidDriver;
    }

    public boolean isIOS() {
        return driver instanceof IOSDriver;
    }

    public void takeScreenshot() throws IOException {
        byte[] fileContent = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
        this.scenario.attach(fileContent, "image/png", this.getStepName());
        String evidenceDirectory = new File("").getAbsolutePath() + "/build/reports/screenshots";
        File screenshot = File.createTempFile("screenshot", ".png",
                new File (evidenceDirectory));
        FileUtils.writeByteArrayToFile(screenshot, fileContent);
    }

    public String getAlertTitle() {
        if (isIOS()) {
            return driver.findElement(By.xpath("//UIAAlert/UIAScrollView/UIAStaticText[1]")).getText();
        } else {
            return driver.findElement(By.id("android:id/alertTitle")).getText();
        }
    }

    public void waitForTime(int time) throws InterruptedException {
        Thread.sleep(time);
    }

    public MobileElement findElement (By by) {
        try {
            return (MobileElement) driver.findElement(by);
        } catch (Exception e) {
            LOGGER.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>> MobileElement findElement e: {}", e.getMessage());
            LOGGER.error(String.valueOf(e));
            return null;
        }
    }

    public List<MobileElement> findElements (By by) {
        try {
            return (List<MobileElement>) driver.findElements(by);
        } catch (Exception e) {
            LOGGER.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>> MobileElement findElements e: {}", e.getMessage());
            LOGGER.error(String.valueOf(e));
            return null;
        }
    }

    public void setScenario(Scenario scenario){
        this.scenario = scenario;
    }
}