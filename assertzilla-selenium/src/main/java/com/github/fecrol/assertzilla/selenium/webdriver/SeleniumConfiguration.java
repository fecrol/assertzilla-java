package com.github.fecrol.assertzilla.selenium.webdriver;

import com.github.fecrol.assertzilla.core.Configuration;

import java.util.HashMap;
import java.util.Map;

public class SeleniumConfiguration implements Configuration {

    private Map<String, WebDriverConfiguration> profiles;

    public SeleniumConfiguration() {
        profiles = new HashMap<>();
    }

    public Map<String, WebDriverConfiguration> getProfiles() {
        return profiles;
    }

    public void setProfiles(Map<String, WebDriverConfiguration> profiles) {
        this.profiles = profiles;
    }
}
