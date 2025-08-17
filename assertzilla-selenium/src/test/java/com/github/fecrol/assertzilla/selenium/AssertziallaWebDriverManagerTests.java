package com.github.fecrol.assertzilla.selenium;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DisplayName("When interacting with the AssertzillaWebDriverManager class")
public class AssertziallaWebDriverManagerTests {

    @BeforeEach
    void setUp() {
        AssertzillaWebDriverManager.resetDriver();
    }

    @Test
    @DisplayName("A new WebDriver instance should be set on initial initialisation")
    void itShouldSetNewWebDriverInstance() {
        // Given
        WebDriver chromeDriver = new ChromeDriver();
        AssertzillaWebDriverManager.initialiseWebDriver(chromeDriver);
        // When
        WebDriver webDriver = AssertzillaWebDriverManager.getDriver();
        // Then
        assertThat(webDriver, is(equalTo(chromeDriver)));
        // Tear Down
        webDriver.quit();
    }

    @Test
    @DisplayName("The WebDriver can be reset successfully")
    void itShouldResetWebDriver() {
        // Given
        WebDriver chromeDriver = new ChromeDriver();
        AssertzillaWebDriverManager.initialiseWebDriver(chromeDriver);
        // When
        AssertzillaWebDriverManager.quitDriver();
        AssertzillaWebDriverManager.resetDriver();
        WebDriver webDriver = AssertzillaWebDriverManager.getDriver();
        // Then
        assertThat(webDriver, is(nullValue()));
    }

    @Test
    @DisplayName("A new WebDriver instance should not be set if a WebDriver instance is set already")
    void itShouldNotSetNewWebDriverWhenWebDriverAlreadySet() {
        // Given
        WebDriver chromeDriver = new ChromeDriver();
        AssertzillaWebDriverManager.initialiseWebDriver(chromeDriver);
        // When
        WebDriver anotherChromeDriver = new ChromeDriver();
        AssertzillaWebDriverManager.initialiseWebDriver(anotherChromeDriver);
        // Then
        WebDriver webDriverManagerWebDriver = AssertzillaWebDriverManager.getDriver();
        assertThat(webDriverManagerWebDriver, is(equalTo(chromeDriver)));
        assertThat(webDriverManagerWebDriver, is(not(equalTo(anotherChromeDriver))));
        // Tear Down
        chromeDriver.quit();
        anotherChromeDriver.quit();
    }
}
