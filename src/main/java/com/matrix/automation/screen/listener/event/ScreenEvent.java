package com.matrix.automation.screen.listener.event;

import com.matrix.automation.screen.AbstractScreen;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class ScreenEvent {
    private Type tpye;
    private AppiumDriver driver;
    private AbstractScreen abstractScreen;
    private MobileElement mobileElement;

    public ScreenEvent(Type tpye, AppiumDriver driver, AbstractScreen abstractScreen, MobileElement mobileElement) {
        this.tpye = tpye;
        this.driver = driver;
        this.abstractScreen = abstractScreen;
        this.mobileElement = mobileElement;
    }

    public Type getTpye() {
        return tpye;
    }

    public void setTpye(Type tpye) {
        this.tpye = tpye;
    }

    public AppiumDriver getDriver() {
        return driver;
    }

    public void setDriver(AppiumDriver driver) {
        this.driver = driver;
    }

    public AbstractScreen getAbstractScreen() {
        return abstractScreen;
    }

    public void setAbstractScreen(AbstractScreen abstractScreen) {
        this.abstractScreen = abstractScreen;
    }

    public MobileElement getMobileElement() {
        return mobileElement;
    }

    public void setMobileElement(MobileElement mobileElement) {
        this.mobileElement = mobileElement;
    }


    public enum Type {
        BEFOREFIELD,
        AFTERFIELD,
        BEFORECLICK,
        AFTERCLICK,
        SCREENCHANGE,
        SCREENERROR
    }
}
