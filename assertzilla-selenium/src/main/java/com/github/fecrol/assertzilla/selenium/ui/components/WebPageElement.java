package com.github.fecrol.assertzilla.selenium.ui.components;

import com.github.fecrol.assertzilla.core.interactions.Wait;
import com.github.fecrol.assertzilla.selenium.AssertzillaWebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class WebPageElement implements FindableWebPageElement {

    private String webElementDescription;
    private By locator;
    private WebPageElement container;

    private WebPageElement(WebPageElement.Builder builder) {
        this.webElementDescription = builder.webElementDescription;
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
        WebDriver webDriver = AssertzillaWebDriverManager.getDriver();
        Wait.until(() -> findAll().stream().findFirst().isPresent()).perform();
        WebElement presentWebElement = findAll().stream().findFirst().get();
        Wait.until(() -> {
            WebElement visibleWebElement = ExpectedConditions.visibilityOf(presentWebElement).apply(webDriver);
            return visibleWebElement != null;
        }).perform();
        return findAll().stream().findFirst().orElseThrow();
    }

    @Override
    public List<WebElement> findAll() {
        WebDriver webDriver = AssertzillaWebDriverManager.getDriver();
        ExpectedCondition<List<WebElement>> presenceOfElementsCondition;

        if (container == null) {
            presenceOfElementsCondition = ExpectedConditions.presenceOfAllElementsLocatedBy(locator);
        } else {
            presenceOfElementsCondition = ExpectedConditions.presenceOfNestedElementsLocatedBy(container.getLocator(), locator);
        }

        List<WebElement> presentWebElements = presenceOfElementsCondition.apply(webDriver);

        return presentWebElements == null ? new ArrayList<>() : presentWebElements;
    }

    public By getLocator() {
        return locator;
    }

    @Override
    public String toString() {
        return container == null ? this.webElementDescription : this.webElementDescription + " inside " + container;
    }

    public static class Builder {

        private String webElementDescription;
        private By locator;

        private Builder(String webElementDescription) {
            this.webElementDescription = webElementDescription;
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

