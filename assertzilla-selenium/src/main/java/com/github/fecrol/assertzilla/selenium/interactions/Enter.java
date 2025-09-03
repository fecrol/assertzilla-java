package com.github.fecrol.assertzilla.selenium.interactions;

import com.github.fecrol.assertzilla.core.interactions.Interaction;
import com.github.fecrol.assertzilla.selenium.ui.components.WebPageElement;

public class Enter {

    private String text;

    private Enter(String text) {
        this.text = text;
    }

    public static Enter text(String text) {
        return new Enter(text);
    }

    public Interaction into(WebPageElement pageElement) {
        return new EnterTextInteraction(pageElement, text);
    }
}
