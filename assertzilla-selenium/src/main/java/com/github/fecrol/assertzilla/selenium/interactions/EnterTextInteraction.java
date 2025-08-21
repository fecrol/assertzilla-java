package com.github.fecrol.assertzilla.selenium.interactions;

import com.github.fecrol.assertzilla.core.annotations.Log;
import com.github.fecrol.assertzilla.core.interactions.Interaction;
import com.github.fecrol.assertzilla.selenium.ui.components.WebElement;

public class EnterTextInteraction implements Interaction {

    private WebElement webElement;
    private String text;

    public EnterTextInteraction(WebElement webElement, String text) {
        this.webElement = webElement;
        this.text = text;
    }

    @Override
    @Log("Enter '${text}' into ${webElement}")
    public void perform() {
        webElement.resolve().sendKeys(text);
    }
}
