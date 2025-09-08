package com.github.fecrol.assertzilla.core.models;

import com.github.fecrol.assertzilla.core.Configuration;

public class HelloWorldConfiguration implements Configuration {

    private String hello;

    public HelloWorldConfiguration() {
    }

    public String getHello() {
        return hello;
    }

    public void setHello(String hello) {
        this.hello = hello;
    }
}
