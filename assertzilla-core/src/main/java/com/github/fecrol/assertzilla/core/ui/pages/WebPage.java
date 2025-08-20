package com.github.fecrol.assertzilla.core.ui.pages;

import com.github.fecrol.assertzilla.core.annotations.Url;

import java.lang.reflect.InvocationTargetException;

public abstract class WebPage {

    public String getUrl() {
        Url urlAnnotation = this.getClass().getAnnotation(Url.class);
        if(urlAnnotation == null) {
            throw new RuntimeException(
                    new StringBuilder()
                            .append("Failed to get URL. ")
                            .append(Url.class)
                            .append(" annotation missing from ")
                            .append(this.getClass().getName())
                            .toString()
            );
        }
        else return urlAnnotation.value();
    }

    public static WebPage instantiate(Class<? extends WebPage> webPageClass) {
        try {
            return webPageClass.getDeclaredConstructor().newInstance();
        }
        catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder().append(this.getClass().getSimpleName());

        try {
            stringBuilder.append(" with URL of ").append(getUrl());
        }
        catch (Exception ignored) {}

        return stringBuilder.toString();
    }
}

