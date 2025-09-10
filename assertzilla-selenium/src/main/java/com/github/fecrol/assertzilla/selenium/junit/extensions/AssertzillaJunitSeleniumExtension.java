package com.github.fecrol.assertzilla.selenium.junit.extensions;

import com.github.fecrol.assertzilla.selenium.webdriver.AssertzillaWebDriverManager;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.chrome.ChromeDriver;

class AssertzillaJunitSeleniumExtension implements BeforeEachCallback, AfterEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        AssertzillaWebDriverManager.resetDriver();
        AssertzillaWebDriverManager.initialiseWebDriver(new ChromeDriver());
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        AssertzillaWebDriverManager.quitDriver();
    }
}
