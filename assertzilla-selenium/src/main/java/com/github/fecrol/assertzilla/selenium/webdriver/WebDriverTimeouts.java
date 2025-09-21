package com.github.fecrol.assertzilla.selenium.webdriver;

public class WebDriverTimeouts {

    private Long pageLoad;
    private Long script;
    private Long implicitWait;

    public WebDriverTimeouts() {
        pageLoad = 300000L;
        script = 30000L;
        implicitWait = 0L;
    }

    public Long getPageLoad() {
        return pageLoad;
    }

    public void setPageLoad(Long pageLoad) {
        this.pageLoad = pageLoad;
    }

    public Long getScript() {
        return script;
    }

    public void setScript(Long script) {
        this.script = script;
    }

    public Long getImplicitWait() {
        return implicitWait;
    }

    public void setImplicitWait(Long implicitWait) {
        this.implicitWait = implicitWait;
    }
}
