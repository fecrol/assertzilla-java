package com.github.fecrol.assertzilla.selenium.interactions;

import com.github.fecrol.assertzilla.core.interactions.Interaction;
import com.github.fecrol.assertzilla.selenium.ui.components.WebElement;

public class Enter {

    private String text;

    private Enter(String text) {
        this.text = text;
    }

    public static Enter text(String text) {
        return new Enter(text);
    }

    public Interaction into(WebElement webElement) {
        return new EnterTextInteraction(webElement, text);
    }
}
