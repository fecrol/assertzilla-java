package com.github.fecrol.assertzilla.selenium.webdriver;

import com.github.fecrol.assertzilla.core.ConfigurationLoader;
import com.github.fecrol.assertzilla.core.exceptions.ConfigurationIOException;
import com.google.common.base.Strings;
import org.openqa.selenium.WebDriver;

public class AssertzillaWebDriverManager {

    private static final ThreadLocal<WebDriverConfiguration> WEB_DRIVER_CONFIGURATION;
    private static final ThreadLocal<WebDriver> WEB_DRIVER;

    static {
        WEB_DRIVER_CONFIGURATION = new ThreadLocal<>();
        WEB_DRIVER = new ThreadLocal<>();
    }

    public static WebDriverConfiguration loadWebDriverConfiguration() {
        if(WEB_DRIVER_CONFIGURATION.get() == null) {
            WebDriverConfiguration webDriverConfiguration;
            String webDriverConfigurationFile = System.getProperty("webdriver");

            if(Strings.isNullOrEmpty(webDriverConfigurationFile)) {
                webDriverConfigurationFile = "default";
            }

            String configurationFileName = "webdriver-%s-configuration.yml".formatted(webDriverConfigurationFile);

            try {
                webDriverConfiguration = ConfigurationLoader
                        .loadResource(configurationFileName)
                        .as(WebDriverConfiguration.class);
            } catch (ConfigurationIOException e) {
                System.out.println(e.getMessage());
                webDriverConfiguration = new WebDriverConfiguration();
            }

            webDriverConfiguration
                    .getCapabilities()
                    .getOptions()
                    .removeIf(item -> item.toLowerCase().contains("headless"));

            if(webDriverConfiguration.getCapabilities().isHeadless()) {
                webDriverConfiguration.getCapabilities().getOptions().add("headless=new");
            }
            if(System.getProperties().containsKey("webdriver.platform")) {
                webDriverConfiguration.getCapabilities().setPlatformName(System.getProperty("webdriver.platform"));
            }

            WEB_DRIVER_CONFIGURATION.set(webDriverConfiguration);
        }
        return WEB_DRIVER_CONFIGURATION.get();
    }

    public static void initialiseWebDriver(WebDriver webDriver) {
        if(WEB_DRIVER.get() == null) {
            WEB_DRIVER.set(webDriver);
        }
    }

    public static void initialiseWebDriver() {
        if(WEB_DRIVER.get() == null) {
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
