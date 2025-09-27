package com.github.fecrol.assertzilla.selenium.webdriver;

import com.github.fecrol.assertzilla.core.ConfigurationLoader;
import com.github.fecrol.assertzilla.core.exceptions.ConfigurationIOException;
import com.google.common.base.Strings;
import org.openqa.selenium.WebDriver;

import java.util.Map;

public class AssertzillaWebDriverManager {

    private static final ThreadLocal<WebDriverConfiguration> WEB_DRIVER_CONFIGURATION;
    private static final ThreadLocal<WebDriver> WEB_DRIVER;

    static {
        WEB_DRIVER_CONFIGURATION = new ThreadLocal<>();
        WEB_DRIVER = new ThreadLocal<>();
    }

    public static WebDriverConfiguration loadWebDriverConfiguration() {
        if (WEB_DRIVER_CONFIGURATION.get() == null) {
            SeleniumConfiguration seleniumConfiguration = loadSeleniumConfiguration();
            WebDriverConfiguration webDriverConfiguration = loadWebDriverConfigurationFrom(seleniumConfiguration);

            webDriverConfiguration
                    .getCapabilities()
                    .getOptions()
                    .removeIf(item -> item.toLowerCase().contains("headless"));

            if (webDriverConfiguration.getCapabilities().isHeadless()) {
                webDriverConfiguration.getCapabilities().getOptions().add("headless=new");
            }

            WEB_DRIVER_CONFIGURATION.set(webDriverConfiguration);
        }
        return WEB_DRIVER_CONFIGURATION.get();
    }

    private static SeleniumConfiguration loadSeleniumConfiguration() {
        SeleniumConfiguration seleniumConfiguration;

        try {
            seleniumConfiguration = ConfigurationLoader
                    .loadResource("selenium-configuration.yml")
                    .node("webdriver")
                    .as(SeleniumConfiguration.class);
        } catch (ConfigurationIOException e) {
            System.out.println(e.getMessage());
            seleniumConfiguration = new SeleniumConfiguration();
        }

        return seleniumConfiguration;
    }

    private static WebDriverConfiguration loadWebDriverConfigurationFrom(SeleniumConfiguration seleniumConfiguration) {
        Map<String, WebDriverConfiguration> webDriverProfiles = seleniumConfiguration.getProfiles();
        String webDriverProfile = System.getProperty("webdriver.profile");
        webDriverProfile = Strings.isNullOrEmpty(webDriverProfile) ? "default" : webDriverProfile;
        WebDriverConfiguration webDriverConfiguration;

        if (webDriverProfiles.containsKey(webDriverProfile)) {
            webDriverConfiguration = webDriverProfiles.get(webDriverProfile);
        } else {
            webDriverConfiguration = new WebDriverConfiguration();
        }

        return webDriverConfiguration;
    }

    public static void initialiseWebDriver(WebDriver webDriver) {
        if (WEB_DRIVER.get() == null) {
            WEB_DRIVER.set(webDriver);
        }
    }

    public static void initialiseWebDriver() {
        if (WEB_DRIVER.get() == null) {
            WebDriverConfiguration webDriverConfiguration = loadWebDriverConfiguration();
            WebDriver webDriver = WebDriverFactory.createWebDriver(webDriverConfiguration);
            WEB_DRIVER.set(webDriver);
        }
    }

    public static WebDriver getDriver() {
        return WEB_DRIVER.get();
    }

    public static void quitDriver() {
        WEB_DRIVER.get().quit();
    }

    public static void resetDriver() {
        WEB_DRIVER.remove();
    }
}
