package com.github.fecrol.assertzilla.core.exceptions;

public class ConfigurationIOException extends RuntimeException {

    public ConfigurationIOException(Exception e) {
        super(e);
    }

    public ConfigurationIOException(String message) {
        super(message);
    }
}
