package com.matrix.automation.factory;

import com.matrix.automation.screen.listener.EvidenceScreenInterceptor;
import com.matrix.automation.screen.listener.ScreenInterceptor;
import io.cucumber.java.Scenario;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AppiumDriverBuilder {
	protected static final Logger LOGGER = LoggerFactory.getLogger(AppiumDriverBuilder.class);
	protected static final ResourceBundle APP_PROP	= ResourceBundle.getBundle("application");
	protected static AppiumDriver driver;

	private String outputDirectory;
	private List<ScreenInterceptor> screenInterceptorList = new ArrayList<ScreenInterceptor>();
	private EvidenceScreenInterceptor evidenceScreenInterceptor;
	AppiumDriverBuilder baseTestBuilder;

	@Autowired
	public AppiumDriverBuilder() {
		LOGGER.info("===>>> AppiumDriverBuilder ****** ");
	}

	public static BaseTest runWithDevice(Scenario scenario) {
		String platform = System.getProperty("platformName");
		if (platform == null | platform.equals(""))
			platform = APP_PROP.getString("platformName");
		LOGGER.info("===>>> platform: {}", platform);

		String appName = System.getProperty("app");
		if (appName == null | appName.equals(""))
			appName = APP_PROP.getString("applicationName");
		LOGGER.info("===>>> appName: {}", appName);

		String deviceName = System.getProperty("deviceName");
		if (deviceName == null | deviceName.equals(""))
			deviceName = APP_PROP.getString("deviceName");
		LOGGER.info("===>>> deviceName: {}", deviceName);

		String platformVersion = System.getProperty("platformVersion");
		if (platformVersion == null | platformVersion.equals(""))
			platformVersion = APP_PROP.getString("platformVersion");
		LOGGER.info("===>>> platformVersion: {}", platformVersion);

		String udid = System.getProperty("udid");
		if (udid == null | udid.equals(""))
			udid = APP_PROP.getString("udid");
		LOGGER.info("===>>> udid: {}", udid);

		DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("platformName", platform);
			capabilities.setCapability("automationName", APP_PROP.getString("automationName"));
			capabilities.setCapability("deviceName", deviceName);
			capabilities.setCapability("platformVersion", platformVersion);
			capabilities.setCapability("udid", udid);
		capabilities.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
		capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "com.matrix.MainActivity");
		capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.matrix.release");

			// No desinstala la aplicacion
			//capabilities.setCapability("noReset", true);
			// o Forza a desinstalar la aplicacion
			capabilities.setCapability("fullReset", true);
			String appPath = new File("").getAbsolutePath() + APP_PROP.getString("applicationDir") + appName;
			capabilities.setCapability("app", appPath);

		if (platform.equalsIgnoreCase("Ios")) {
			try {
				driver = new IOSDriver(new URL(APP_PROP.getString("driverURL")), capabilities);
			} catch (MalformedURLException e) {
				LOGGER.info("===>>> Error al crear IOSDriver: {}", e.getMessage());
				LOGGER.error(String.valueOf(e));
			}
		} else if (platform.equalsIgnoreCase("Android")) {
			try {
				driver = new AndroidDriver(new URL(APP_PROP.getString("driverURL")), capabilities);
			} catch (MalformedURLException e) {
				LOGGER.info("===>>> Error al crear AndroidDriver: {}", e.getMessage());
				LOGGER.error(String.valueOf(e));
			}
		} else {
			try {
				throw new Exception("Unable to read device platform.");
			} catch (Exception e) {
				LOGGER.error(String.valueOf(e));
			}
		}
		return new BaseTest (driver, scenario);
    }

	public static BaseTest runWithEmulator(Scenario scenario) {
		String platform = System.getProperty("platformName");
		if (platform == null | platform.equals(""))
			platform = APP_PROP.getString("platformName");
		LOGGER.info("===>>> platform: {}", platform);
		
		String appName = System.getProperty("app");
		if (appName == null | appName.equals(""))
			appName = APP_PROP.getString("applicationName");
		LOGGER.info("===>>> appName: {}", appName);

		String avd = System.getProperty("avd");
		if (avd == null | avd.equals(""))
			avd = APP_PROP.getString("avd");
		LOGGER.info("===>>> avd: {}", avd);

		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("platformName", platform);
		capabilities.setCapability("automationName", APP_PROP.getString("automationName"));
		capabilities.setCapability(AndroidMobileCapabilityType.AVD, avd);
		capabilities.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
		capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "com.matrix.MainActivity");
		capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.matrix.release");
		//capabilities.setCapability(AndroidMobileCapabilityType.AVD_LAUNCH_TIMEOUT, 30);
		//capabilities.setCapability(AndroidMobileCapabilityType.AVD_READY_TIMEOUT, 30);
		// No desinstala la aplicacion
		//capabilities.setCapability("noReset", true);
		// o Forza desinstalar la aplicacion
		capabilities.setCapability("fullReset", true);
		String appPath = new File("").getAbsolutePath() + APP_PROP.getString("applicationDir") + appName;
		capabilities.setCapability("app", appPath);

		if (platform.equalsIgnoreCase("Ios")) {
			try {
				driver = new IOSDriver(new URL(APP_PROP.getString("driverURL")), capabilities);
			} catch (MalformedURLException e) {
				LOGGER.info("===>>> Error al crear IOSDriver: {}", e.getMessage());
				LOGGER.error(String.valueOf(e));
			}
		} else if (platform.equalsIgnoreCase("Android")) {
			try {
				driver = new AndroidDriver(new URL(APP_PROP.getString("driverURL")), capabilities);
			} catch (MalformedURLException e) {
				LOGGER.info("===>>> Error al crear AndroidDriver: {}", e.getMessage());
				LOGGER.error(String.valueOf(e));
			}
		} else {
			try {
				throw new Exception("Unable to read device platform.");
			} catch (Exception e) {
				LOGGER.error(String.valueOf(e));
			}
		}
		return new BaseTest (driver, scenario);
	}

	public String getOutputDirectory() {
		return this.outputDirectory;
	}

	public EvidenceScreenInterceptor getEvidenceScreenInterceptor() {
		return evidenceScreenInterceptor;
	}

	public AppiumDriverBuilder enableEvidenceCollector(String testName) {
		evidenceScreenInterceptor = new EvidenceScreenInterceptor(testName, false, true, true, getEvidenceDir());
		addScreenListener(evidenceScreenInterceptor);
		return this;
	}

	private AppiumDriverBuilder addScreenListener(ScreenInterceptor screenInterceptor) {
		screenInterceptorList.add(screenInterceptor);
		return this;
	}

	public List<ScreenInterceptor> getScreenInterceptorList() {
		return this.screenInterceptorList;
	}

	public String getEvidenceDir() {
		return new File("").getAbsolutePath() + "/build/reports/screenshots";
	}
 }