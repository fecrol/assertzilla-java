package com.github.fecrol.assertzilla.selenium.interactions;

import com.github.fecrol.assertzilla.core.annotations.Log;
import com.github.fecrol.assertzilla.core.interactions.Interaction;
import com.github.fecrol.assertzilla.selenium.webdriver.AssertzillaWebDriverManager;

class OpenBrowserOnStringUrl implements Interaction {

    private String url;

    public OpenBrowserOnStringUrl(String url) {
        this.url = url;
    }

    @Override
    @Log("Open browser on ${url}")
    public void perform() {
        AssertzillaWebDriverManager.getDriver().get(url);
    }
}
