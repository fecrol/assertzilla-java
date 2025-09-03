package com.github.fecrol.assertzilla.selenium.interactions;

import com.github.fecrol.assertzilla.core.annotations.Log;
import com.github.fecrol.assertzilla.core.interactions.Interaction;
import com.github.fecrol.assertzilla.selenium.ui.components.WebPageElement;

public class Click implements Interaction {

    private WebPageElement pageElement;

    private Click(WebPageElement pageElement) {
        this.pageElement = pageElement;
    }

    public static Click on(WebPageElement pageElement) {
        return new Click(pageElement);
    }

    @Override
    @Log("Click on ${webElement}")
    public void perform() {
        pageElement.find().click();
    }
}
