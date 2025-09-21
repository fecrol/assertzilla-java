package com.github.fecrol.assertzilla.selenium.webdriver;

import com.google.common.base.Strings;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.openqa.selenium.safari.SafariOptions;

import java.time.Duration;

import static java.time.temporal.ChronoUnit.MILLIS;

public class WebDriverOptionsFactory {

    public static AbstractDriverOptions<?> createWebDriverOptions(WebDriverConfiguration webDriverConfiguration) {
        AbstractDriverOptions<?> webDriverOptions;
        WebDriverCapabilities webDriverCapabilities = webDriverConfiguration.getCapabilities();

        switch (webDriverConfiguration.getDriverName().toLowerCase()) {
            case "chrome" -> {
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments(webDriverCapabilities.getOptions());

                webDriverOptions = chromeOptions;
            }
            case "edge" -> {
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments(webDriverCapabilities.getOptions());

                webDriverOptions = edgeOptions;
            }
            case "firefox" -> {
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments(webDriverCapabilities.getOptions());

                webDriverOptions = firefoxOptions;
            }
            case "safari" -> webDriverOptions = new SafariOptions();
            default -> throw new IllegalArgumentException();
        }

        if(!Strings.isNullOrEmpty(webDriverCapabilities.getPlatformName())) {
            webDriverOptions.setPlatformName(webDriverCapabilities.getPlatformName());
        }

        webDriverOptions
                .setAcceptInsecureCerts(webDriverCapabilities.isAcceptInsecureCerts())
                .setPageLoadStrategy(PageLoadStrategy.fromString(webDriverCapabilities.getPageLoadStrategy()))
                .setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.fromString(webDriverCapabilities.getUnhandledPromptBehavior()))
                .setStrictFileInteractability(webDriverCapabilities.isStrictFileInteractability())
                .setPageLoadTimeout(Duration.of(webDriverCapabilities.getTimeouts().getPageLoad(), MILLIS))
                .setScriptTimeout(Duration.of(webDriverCapabilities.getTimeouts().getScript(), MILLIS))
                .setImplicitWaitTimeout(Duration.of(webDriverCapabilities.getTimeouts().getImplicitWait(), MILLIS));

        return webDriverOptions;
    }
}
