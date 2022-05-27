package com.matrix.automation.factory;

import com.matrix.automation.screen.AbstractScreen;
import com.matrix.automation.screen.listener.EvidenceScreenInterceptor;
import com.matrix.automation.screen.listener.ScreenInterceptor;
import io.cucumber.java.Scenario;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BaseTest {
    protected static final Logger LOGGER = LoggerFactory.getLogger(BaseTest.class);
    private AppiumDriver driver;
    private AppiumDriverBuilder builder;
    private Scenario scenario;

    private Map<Class, AbstractScreen<?>> screens = new LinkedHashMap<>();
    private List<String> steps = new ArrayList<String>();

    private AbstractScreen<?> currentScreen = null;
    private String currentStep;

    public BaseTest (@SuppressWarnings("rawtypes") AppiumDriver driver, Scenario scenario) {
        this.driver = driver;
        this.scenario = scenario;
    }

    @SuppressWarnings("rawtypes")
	public AppiumDriver getDriver() {
        return this.driver;
    }

    public void setAppiumDriverBuilder (AppiumDriverBuilder builder) {
        this.builder = builder;
    }
    
    @SuppressWarnings("rawtypes")
    public <T extends AbstractScreen> T getScreen(Class<T> class1) {
        try {
            if (!screens.containsKey(class1)) {
                Constructor<T> constructor = class1.getConstructor(AppiumDriver.class);
                LOGGER.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Found constructor for class 2: {}", class1);
                Object instance = constructor.newInstance(this.driver);
                AbstractScreen<?> abstractScreen = (AbstractScreen<?>) instance;
                WebDriverWait wait = new WebDriverWait(driver, 5);
                String ctx = driver.getContext();
                LOGGER.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Found ctx: {}", ctx);
                PageFactory.initElements(new AppiumFieldDecorator(driver.context(ctx), Duration.ofSeconds(1)), abstractScreen);

                List<ScreenInterceptor> interceptors = builder.getScreenInterceptorList();
                if (interceptors != null) {
                    for (ScreenInterceptor screenInterceptor : interceptors) {
                        LOGGER.debug("Adding screem interceptor 2 {} to {}", screenInterceptor.getClass(), class1);
                        abstractScreen.setEvidenceScreenInterceptor((EvidenceScreenInterceptor) screenInterceptor);
                        abstractScreen.addScreenInterceptor(screenInterceptor);
                    }
                }
                LOGGER.info("Instance created class {}", class1);
                screens.put(class1, abstractScreen);
                abstractScreen.setSteps(steps);
                abstractScreen.setScenario(this.scenario);
                LOGGER.debug("Instance setSteps {}", steps);
            }
            currentScreen = screens.get(class1);
            LOGGER.debug("Instance currentScreen {}", currentScreen);
            currentStep = currentScreen.getStepName();
            LOGGER.info("Instance currentStep {}", currentStep);
            return (T) screens.get(class1);
        } catch (Exception e) {
            LOGGER.error("Creating page instance ", e);
        }
        throw new RuntimeException("Error creating page " + class1.getSimpleName());
    }
}
