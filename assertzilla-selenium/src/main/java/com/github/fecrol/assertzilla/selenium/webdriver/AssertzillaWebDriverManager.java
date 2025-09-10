package com.github.fecrol.assertzilla.selenium.webdriver;

import org.openqa.selenium.WebDriver;

public class AssertzillaWebDriverManager {

    private static final ThreadLocal<WebDriver> WEB_DRIVER;

    static {
        WEB_DRIVER = new ThreadLocal<>();
    }

    public static void initialiseWebDriver(WebDriver webDriver) {
        if(WEB_DRIVER.get() == null) {
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
