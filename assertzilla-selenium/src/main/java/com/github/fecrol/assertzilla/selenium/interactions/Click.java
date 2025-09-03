package com.github.fecrol.assertzilla.selenium.interactions;

import com.github.fecrol.assertzilla.core.annotations.Log;
import com.github.fecrol.assertzilla.core.interactions.Interaction;
import com.github.fecrol.assertzilla.selenium.ui.components.WebElement;

public class Click implements Interaction {

    private WebElement webElement;

    private Click(WebElement webElement) {
        this.webElement = webElement;
    }

    public static Click on(WebElement webElement) {
        return new Click(webElement);
    }

    @Override
    @Log("Click on ${webElement}")
    public void perform() {
        webElement.find().click();
    }
}
