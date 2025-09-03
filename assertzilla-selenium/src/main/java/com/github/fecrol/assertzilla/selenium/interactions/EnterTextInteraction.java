package com.github.fecrol.assertzilla.selenium.interactions;

import com.github.fecrol.assertzilla.core.annotations.Log;
import com.github.fecrol.assertzilla.core.interactions.Interaction;
import com.github.fecrol.assertzilla.selenium.ui.components.WebPageElement;

public class EnterTextInteraction implements Interaction {

    private WebPageElement pageElement;
    private String text;

    public EnterTextInteraction(WebPageElement pageElement, String text) {
        this.pageElement = pageElement;
        this.text = text;
    }

    @Override
    @Log("Enter '${text}' into ${webElement}")
    public void perform() {
        pageElement.find().sendKeys(text);
    }
}
