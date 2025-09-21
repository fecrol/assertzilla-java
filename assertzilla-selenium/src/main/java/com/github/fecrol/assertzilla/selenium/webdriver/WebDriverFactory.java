package com.github.fecrol.assertzilla.selenium.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

public class WebDriverFactory {

    public static WebDriver createWebDriver(WebDriverConfiguration webDriverConfiguration) {
        WebDriver webDriver;
        AbstractDriverOptions<?> webDriverOptions = WebDriverOptionsFactory.createWebDriverOptions(webDriverConfiguration);

        switch (webDriverConfiguration.getDriverName().toLowerCase()) {
            case "chrome" -> {
                ChromeOptions chromeOptions = (ChromeOptions) webDriverOptions;
                webDriver = new ChromeDriver(chromeOptions);
            }
            case "edge" -> {
                EdgeOptions edgeOptions = (EdgeOptions) webDriverOptions;
                webDriver = new EdgeDriver(edgeOptions);
            }
            case "firefox" -> {
                FirefoxOptions firefoxOptions = (FirefoxOptions) webDriverOptions;
                webDriver = new FirefoxDriver(firefoxOptions);
            }
            case "safari" -> {
                SafariOptions safariOptions = (SafariOptions) webDriverOptions;
                webDriver = new SafariDriver(safariOptions);
            }
            default -> throw new IllegalArgumentException();
        }

        return webDriver;
    }
}
