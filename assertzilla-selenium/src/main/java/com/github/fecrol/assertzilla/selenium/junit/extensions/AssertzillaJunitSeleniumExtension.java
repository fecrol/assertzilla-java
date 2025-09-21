package com.github.fecrol.assertzilla.selenium.junit.extensions;

import com.github.fecrol.assertzilla.selenium.webdriver.AssertzillaWebDriverManager;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

class AssertzillaJunitSeleniumExtension implements BeforeAllCallback, BeforeEachCallback, AfterEachCallback {

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        AssertzillaWebDriverManager.loadWebDriverConfiguration();
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        AssertzillaWebDriverManager.resetDriver();
        AssertzillaWebDriverManager.initialiseWebDriver();
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        AssertzillaWebDriverManager.quitDriver();
    }
}
