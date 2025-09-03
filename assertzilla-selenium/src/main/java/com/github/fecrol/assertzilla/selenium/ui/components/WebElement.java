package com.github.fecrol.assertzilla.selenium.ui.components;

import com.github.fecrol.assertzilla.core.interactions.Wait;
import com.github.fecrol.assertzilla.selenium.AssertzillaWebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class WebElement implements FindableWebElement {

    private String webElementDescription;
    private By locator;
    private WebElement container;

    private WebElement(WebElement.Builder builder) {
        this.webElementDescription = builder.webElementDescription;
        this.locator = builder.locator;
    }

    public static WebElement.Builder describedAs(String webElementDescription) {
        return new WebElement.Builder(webElementDescription);
    }

    public WebElement inside(WebElement containerElement) {
        this.container = containerElement;
        return this;
    }

    @Override
    public org.openqa.selenium.WebElement find() {
        return findAll().stream().findFirst().orElseThrow();
    }

    @Override
    public List<org.openqa.selenium.WebElement> findAll() {
        WebDriver webDriver = AssertzillaWebDriverManager.getDriver();
        ExpectedCondition<List<org.openqa.selenium.WebElement>> presenceOfElementsCondition;
        ExpectedCondition<List<org.openqa.selenium.WebElement>> visibilityOfElementsCondition;

        if (container == null) {
            presenceOfElementsCondition = ExpectedConditions.presenceOfAllElementsLocatedBy(locator);
            visibilityOfElementsCondition = ExpectedConditions.visibilityOfAllElementsLocatedBy(locator);
        } else {
            presenceOfElementsCondition = ExpectedConditions.presenceOfNestedElementsLocatedBy(container.getLocator(), locator);
            visibilityOfElementsCondition = ExpectedConditions.visibilityOfNestedElementsLocatedBy(container.getLocator(), locator);
        }

        Wait.until(() -> {
            List<org.openqa.selenium.WebElement> presentElements = presenceOfElementsCondition.apply(webDriver);
            return presentElements != null && !presentElements.isEmpty();
        }).perform();

        Wait.until(() -> {
            List<org.openqa.selenium.WebElement> visibleElements = visibilityOfElementsCondition.apply(webDriver);
            return visibleElements != null && !visibleElements.isEmpty();
        }).perform();

        return visibilityOfElementsCondition.apply(webDriver);
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

        public WebElement located(By locator) {
            this.locator = locator;
            return build();
        }

        private WebElement build() {
            return new WebElement(this);
        }
    }
}

