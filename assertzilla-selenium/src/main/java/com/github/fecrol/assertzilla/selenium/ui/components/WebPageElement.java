package com.github.fecrol.assertzilla.selenium.ui.components;

import com.github.fecrol.assertzilla.core.interactions.Wait;
import com.github.fecrol.assertzilla.selenium.webdriver.AssertzillaWebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class WebPageElement implements FindableWebPageElement {

    private WebDriver webDriver;
    private String description;
    private By locator;
    private WebPageElement container;

    private WebPageElement(WebPageElement.Builder builder) {
        this.webDriver = AssertzillaWebDriverManager.getDriver();
        this.description = builder.description;
        this.locator = builder.locator;
    }

    public static WebPageElement.Builder describedAs(String webElementDescription) {
        return new WebPageElement.Builder(webElementDescription);
    }

    public WebPageElement inside(WebPageElement containerElement) {
        this.container = containerElement;
        return this;
    }

    @Override
    public WebElement find() {
        Wait.until(() -> findAll().stream().findFirst().isPresent())
                .orComplainWith(new NoSuchElementException(String.format(
                        "Unable to find %s located %s",
                        description,
                        locator
                )))
                .perform();
        WebElement presentWebElement = findAll().stream().findFirst().get();
        Wait.until(() -> {
            WebElement visibleWebElement = ExpectedConditions.visibilityOf(presentWebElement).apply(webDriver);
            return visibleWebElement != null;
        }).orComplainWith(new ElementNotInteractableException(String.format(
                "Unable to interact with %s located %s",
                        description,
                        locator
                )))
                .perform();

        return findAll()
                .stream()
                .findFirst()
                .orElseThrow();
    }

    @Override
    public List<WebElement> findAll() {
        ExpectedCondition<List<WebElement>> presenceOfElementsCondition;

        if (container == null) {
            presenceOfElementsCondition = ExpectedConditions.presenceOfAllElementsLocatedBy(locator);
        } else {
            presenceOfElementsCondition = ExpectedConditions.presenceOfNestedElementsLocatedBy(container.getLocator(), locator);
        }

        List<WebElement> presentWebElements = null;

        try {
            presentWebElements = presenceOfElementsCondition.apply(webDriver);
        } catch (NoSuchElementException ignored) {}

        return presentWebElements == null ? new ArrayList<>() : presentWebElements;
    }

    public By getLocator() {
        return locator;
    }

    @Override
    public String toString() {
        return container == null ? this.description : this.description + " inside " + container;
    }

    public static class Builder {

        private String description;
        private By locator;

        private Builder(String webElementDescription) {
            this.description = webElementDescription;
        }

        public WebPageElement located(By locator) {
            this.locator = locator;
            return build();
        }

        private WebPageElement build() {
            return new WebPageElement(this);
        }
    }
}

