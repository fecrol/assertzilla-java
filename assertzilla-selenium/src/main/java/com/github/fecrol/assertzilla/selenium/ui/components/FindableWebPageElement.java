package com.github.fecrol.assertzilla.selenium.ui.components;

import org.openqa.selenium.WebElement;

import java.util.List;

public interface FindableWebPageElement {
    WebElement find();
    List<WebElement> findAll();
}
