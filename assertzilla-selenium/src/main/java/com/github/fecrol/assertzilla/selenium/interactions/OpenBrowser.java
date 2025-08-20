package com.github.fecrol.assertzilla.selenium.interactions;

import com.github.fecrol.assertzilla.core.interactions.Interaction;
import com.github.fecrol.assertzilla.core.ui.pages.WebPage;

public class OpenBrowser {

    public static Interaction on(String url) {
        return new OpenBrowserOnStringUrl(url);
    }

    public static Interaction on(Class<? extends WebPage> webPage) {
        return new OpenBrowserOnWebPage(webPage);
    }
}
