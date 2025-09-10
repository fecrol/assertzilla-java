package com.github.fecrol.assertzilla.selenium.ui.components;

import com.github.fecrol.assertzilla.selenium.webdriver.AssertzillaWebDriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.net.URISyntaxException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DisplayName("When interacting with the WebPageElement class")
public class WebPageElementTests {

    @Nested
    @DisplayName("With WebDriver integration")
    class WithWebDriverIntegration {

        private String webPage;

        @BeforeEach
        void setUp() throws URISyntaxException {
            AssertzillaWebDriverManager.resetDriver();
            String htmlFile = "artifacts/html/web-page-element-tests.html";
            webPage = this.getClass().getClassLoader().getResource(htmlFile).toURI().toString();
            WebDriver webDriver = new ChromeDriver(new ChromeOptions().addArguments("--headless=new"));
            AssertzillaWebDriverManager.initialiseWebDriver(webDriver);
        }

        @AfterEach
        void tearDown() {
            AssertzillaWebDriverManager.quitDriver();
        }

        @ParameterizedTest
        @DisplayName("All existent elements should be found")
        @CsvSource(value = {"#single-element", ".multiple-elements"})
        void itShouldFindElementsForExistentLocator(String selector) {
            // Given
            AssertzillaWebDriverManager.getDriver().get(webPage);
            WebPageElement webPageElement = WebPageElement
                    .describedAs(selector)
                    .located(By.cssSelector(selector));
            // When
            List<WebElement> locatedWebElements = webPageElement.findAll();
            // Then
            assertThat(locatedWebElements.size(), is(greaterThanOrEqualTo(1)));
        }

        @Test
        @DisplayName("An existent element should be found")
        void itShouldFindElementForMatchingLocator() {
            // Given
            AssertzillaWebDriverManager.getDriver().get(webPage);
            WebPageElement webPageElement = WebPageElement
                    .describedAs("some existing element")
                    .located(By.cssSelector("#single-element"));
            // When
            WebElement locatedWebElement = webPageElement.find();
            // Then
            assertThat(locatedWebElement, is(notNullValue()));
        }

        @Test
        @DisplayName("An existent element that becomes present after a delay should be found")
        void itShouldFindElementsWithDelayedPresence() {
            // Given
            AssertzillaWebDriverManager.getDriver().get(webPage);
            WebPageElement webPageElement = WebPageElement
                    .describedAs("some element with delayed presence")
                    .located(By.cssSelector("#delayed-presence-element"));
            // When
            WebElement locatedWebElement = webPageElement.find();
            // Then
            assertThat(locatedWebElement, is(notNullValue()));
        }

        @Test
        @DisplayName("An existent element that becomes visible after a delay should be found")
        void itShouldFindElementsWithDelayedVisibility() {
            // Given
            AssertzillaWebDriverManager.getDriver().get(webPage);
            WebPageElement webPageElement = WebPageElement
                    .describedAs("some element with delayed visibility")
                    .located(By.cssSelector("#delayed-visibility-element"));
            // When
            WebElement locatedWebElement = webPageElement.find();
            // Then
            assertThat(locatedWebElement, is(notNullValue()));
        }

        @Test
        @DisplayName("No elements should be found for a non existent locator")
        void itShouldFindNoElementsForNonExistentLocator() {
            // Given
            AssertzillaWebDriverManager.getDriver().get(webPage);
            WebPageElement webPageElement = WebPageElement
                    .describedAs("non existent element")
                    .located(By.id("non-existent-element"));
            // When
            List<WebElement> locatedWebElements = webPageElement.findAll();
            // Then
            assertThat(locatedWebElements.size(), is(equalTo(0)));
        }

        @Test
        @DisplayName("No elements should be found for a non existent child locator inside an existent parent locator")
        void itShouldFindNoElementsForNonExistentChildLocatorInsideExistentParentLocator() {
            // Given
            AssertzillaWebDriverManager.getDriver().get(webPage);
            WebPageElement parentElement = WebPageElement
                    .describedAs("existent parent element")
                    .located(By.id("parent-element"));
            WebPageElement nestedChildElement = WebPageElement
                    .describedAs("non existent child element")
                    .located(By.id("non-existent-child-element"))
                    .inside(parentElement);
            // When
            List<WebElement> locatedWebElements = nestedChildElement.findAll();
            // Then
            assertThat(locatedWebElements.size(), is(equalTo(0)));
        }

        @Test
        @DisplayName("No elements should be found for a non existent child locator inside a non existent parent locator")
        void itShouldFindNoElementsForNonExistentChildLocatorInsideNonExistentParentLocator() {
            // Given
            AssertzillaWebDriverManager.getDriver().get(webPage);
            WebPageElement parentElement = WebPageElement
                    .describedAs("non existent parent element")
                    .located(By.id("non-existent-parent-element"));
            WebPageElement nestedChildElement = WebPageElement
                    .describedAs("non existent child element")
                    .located(By.id("non-existent-child-element"))
                    .inside(parentElement);
            // When
            List<WebElement> locatedWebElements = nestedChildElement.findAll();
            // Then
            assertThat(locatedWebElements.size(), is(equalTo(0)));
        }

        @Test
        @DisplayName("No Such Element Exception should be thrown for non existent elements")
        void itShouldThrowNoSuchElementExceptionForNonExistentLocators() {
            // Given
            AssertzillaWebDriverManager.getDriver().get(webPage);
            WebPageElement webPageElement = WebPageElement
                    .describedAs("some non-existent existing element")
                    .located(By.id("non-existent-element"));
            // Then
            assertThatThrownBy(webPageElement::find).isInstanceOf(NoSuchElementException.class);
        }

        @Test
        @DisplayName("Element Not Interactable Exception should be thrown for present but invisible elements")
        void itShouldThrowElementNotInteractableExceptionForPresentButInvisibleElements() {
            // Given
            AssertzillaWebDriverManager.getDriver().get(webPage);
            WebPageElement webPageElement = WebPageElement
                    .describedAs("some present but invisible element")
                    .located(By.id("invisible-element"));
            // Then
            assertThatThrownBy(webPageElement::find).isInstanceOf(ElementNotInteractableException.class);
        }
    }

    @Nested
    @DisplayName("Without WebDriver integration")
    class withoutWebDriverIntegration {

        @Test
        @DisplayName("The expected description should be returned for non nested elements")
        void itShouldReturnTheExpectedDescriptionForNonNestedElements() {
            // Given
            WebPageElement webPageElement = WebPageElement
                    .describedAs("my funky element")
                    .located(null);
            // When
            String webPageElementDescription = webPageElement.toString();
            // Then
            assertThat(webPageElementDescription, is(equalTo("my funky element")));
        }

        @Test
        @DisplayName("The expected description should be returned for nested elements")
        void itShouldReturnTheExpectedDescriptionForNestedElements() {
            // Given
            WebPageElement parentElement = WebPageElement
                    .describedAs("my funky parent element")
                    .located(null);
            WebPageElement childElement =  WebPageElement
                    .describedAs("my funky child element")
                    .located(null)
                    .inside(parentElement);
            // When
            String childWebPageElementDescription = childElement.toString();
            // Then
            assertThat(
                    childWebPageElementDescription,
                    is(equalTo("my funky child element inside my funky parent element"))
            );
        }

        @Test
        @DisplayName("The expected description should be returned for the parent element of nested elements")
        void itShouldReturnTheExpectedDescriptionOfTheParentElementOfNestedElements() {
            // Given
            WebPageElement parentElement = WebPageElement
                    .describedAs("my funky parent element")
                    .located(null);
            WebPageElement childElement =  WebPageElement
                    .describedAs("my funky child element")
                    .located(null)
                    .inside(parentElement);
            // When
            String parentWebPageElementDescription = parentElement.toString();
            // Then
            assertThat(parentWebPageElementDescription, is(equalTo("my funky parent element")));
        }

        @Test
        void itShouldCorrectlySetTheLocator() {
            // Given
            By byLocator = By.id("funky-element");
            WebPageElement webPageElement = WebPageElement
                    .describedAs("my funky element")
                    .located(byLocator);
            // When
            By webPageElementLocator = webPageElement.getLocator();
            // Then
            assertThat(webPageElementLocator, is(equalTo(byLocator)));
        }
    }
}
