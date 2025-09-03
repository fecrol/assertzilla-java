package com.github.fecrol.assertzilla.selenium.ui.components;

import org.openqa.selenium.WebElement;

import java.util.List;

public interface FindableWebElement {
    WebElement find();
    List<WebElement> findAll();
}
