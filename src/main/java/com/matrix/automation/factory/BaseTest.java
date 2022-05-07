package com.matrix.automation.factory;

import com.matrix.automation.screen.AbstractScreen;
import com.matrix.automation.screen.listener.EvidenceScreenInterceptor;
import com.matrix.automation.screen.listener.ScreenInterceptor;
import cucumber.api.Scenario;
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

    public BaseTest (AppiumDriver driver, Scenario scenario) {
        this.driver = driver;
        this.scenario = scenario;
    }

    public AppiumDriver getDriver() {
        return this.driver;
    }

    public void setAppiumDriverBuilder (AppiumDriverBuilder builder) {
        this.builder = builder;
    }

    public <T extends AbstractScreen<?>> T getScreen(Class<T> clazz) {
        try {
            if (!screens.containsKey(clazz)) {
                Constructor<T> constructor = clazz.getConstructor(AppiumDriver.class);
                LOGGER.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Found constructor for class 2: {}", clazz);
                Object instance = constructor.newInstance(this.driver);
                AbstractScreen<?> abstractScreen = (AbstractScreen<?>) instance;
                WebDriverWait wait = new WebDriverWait(driver, 5);
                String ctx = driver.getContext();
                LOGGER.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>> Found ctx: {}", ctx);
                PageFactory.initElements(new AppiumFieldDecorator(driver.context(ctx), Duration.ofSeconds(1)), abstractScreen);

                List<ScreenInterceptor> interceptors = builder.getScreenInterceptorList();
                if (interceptors != null) {
                    for (ScreenInterceptor screenInterceptor : interceptors) {
                        LOGGER.debug("Adding screem interceptor 2 {} to {}", screenInterceptor.getClass(), clazz);
                        abstractScreen.setEvidenceScreenInterceptor((EvidenceScreenInterceptor) screenInterceptor);
                        abstractScreen.addScreenInterceptor(screenInterceptor);
                    }
                }

                LOGGER.info("Instance created class {}", clazz);
                screens.put(clazz, abstractScreen);
                //if (currentScreen != null && currentScreen instanceof FolioInfoFinancieraPage) {
                //    currentScreen.resetStepName();
                //}
                abstractScreen.setSteps(steps);
                abstractScreen.setScenario(this.scenario);
                LOGGER.debug("Instance setSteps {}", steps);
            }
            currentScreen = screens.get(clazz);
            LOGGER.debug("Instance currentScreen {}", currentScreen);
            currentStep = currentScreen.getStepName();
            LOGGER.info("Instance currentStep {}", currentStep);

            /*if (customer != null && (customer.getSessionId() == null || customer.getSessionId().isEmpty()) ) {
                customer.setSessionId(currentPage.getSessionId());
            }*/

            return (T) screens.get(clazz);
        } catch (Exception e) {
            LOGGER.error("Creating page instance mmmmmmmmmmmmmmmmmmmmm", e);
            e.printStackTrace();
        }
        throw new RuntimeException("Error creating page " + clazz.getSimpleName());
        //return null;
    }
}
