package com.github.fecrol.assertzilla.selenium.webdriver;

import com.github.fecrol.assertzilla.core.Configuration;

public class WebDriverConfiguration implements Configuration {

    private String driverName;
    private String driverPath;
    private WebDriverCapabilities capabilities;

    public WebDriverConfiguration() {
        driverName = "chrome";
        capabilities = new WebDriverCapabilities();
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverPath() {
        return driverPath;
    }

    public void setDriverPath(String driverPath) {
        this.driverPath = driverPath;
    }

    public WebDriverCapabilities getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(WebDriverCapabilities capabilities) {
        this.capabilities = capabilities;
    }
}
