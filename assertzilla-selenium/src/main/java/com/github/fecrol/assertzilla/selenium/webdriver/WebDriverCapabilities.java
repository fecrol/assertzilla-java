package com.github.fecrol.assertzilla.selenium.webdriver;

import java.util.ArrayList;

public class WebDriverCapabilities {

    private String platformName;
    private boolean acceptInsecureCerts;
    private String pageLoadStrategy;
    private String unhandledPromptBehavior;
    private boolean strictFileInteractability;
    private boolean headless;
    private WebDriverTimeouts timeouts;
    private ArrayList<String> options;

    public WebDriverCapabilities() {
        acceptInsecureCerts = true;
        pageLoadStrategy = "normal";
        unhandledPromptBehavior = "ignore";
        strictFileInteractability = false;
        headless = true;
        timeouts = new WebDriverTimeouts();
        // TODO: create factory for default options for different drivers (chromium, gecko, safari)
        options = new ArrayList<>();
        options.add("remote-allow-origins=*");
        options.add("incognito");
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public boolean isAcceptInsecureCerts() {
        return acceptInsecureCerts;
    }

    public void setAcceptInsecureCerts(boolean acceptInsecureCerts) {
        this.acceptInsecureCerts = acceptInsecureCerts;
    }

    public String getPageLoadStrategy() {
        return pageLoadStrategy;
    }

    public void setPageLoadStrategy(String pageLoadStrategy) {
        this.pageLoadStrategy = pageLoadStrategy;
    }

    public String getUnhandledPromptBehavior() {
        return unhandledPromptBehavior;
    }

    public void setUnhandledPromptBehavior(String unhandledPromptBehavior) {
        this.unhandledPromptBehavior = unhandledPromptBehavior;
    }

    public boolean isStrictFileInteractability() {
        return strictFileInteractability;
    }

    public void setStrictFileInteractability(boolean strictFileInteractability) {
        this.strictFileInteractability = strictFileInteractability;
    }

    public boolean isHeadless() {
        return headless;
    }

    public void setHeadless(boolean headless) {
        this.headless = headless;
    }

    public WebDriverTimeouts getTimeouts() {
        return timeouts;
    }

    public void setTimeouts(WebDriverTimeouts timeouts) {
        this.timeouts = timeouts;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }
}
