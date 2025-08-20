package com.github.fecrol.assertzilla.selenium.interactions;

import com.github.fecrol.assertzilla.core.annotations.Log;
import com.github.fecrol.assertzilla.core.annotations.Report;
import com.github.fecrol.assertzilla.core.interactions.Interaction;
import com.github.fecrol.assertzilla.core.ui.pages.WebPage;

class OpenBrowserOnWebPage implements Interaction {

    private WebPage webPage;

    public OpenBrowserOnWebPage(Class<? extends WebPage> webPage) {
        this.webPage = WebPage.instantiate(webPage);
    }

    @Override
    @Log("Open browser on ${webPage}")
    @Report("Open browser on ${webPage}")
    public void perform() {
        new OpenBrowserOnStringUrl(webPage.getUrl()).perform();
    }
}
