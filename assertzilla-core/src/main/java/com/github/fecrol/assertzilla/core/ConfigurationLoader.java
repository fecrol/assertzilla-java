package com.github.fecrol.assertzilla.core;

import com.github.fecrol.assertzilla.core.exceptions.ConfigurationIOException;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;

public class ConfigurationLoader {

    private Yaml YAML;
    private String configurationFile;

    private ConfigurationLoader(String configurationFile) {
        YAML = new Yaml();
        this.configurationFile = configurationFile;
    }

    public static ConfigurationLoader loadResource(String configurationName) {
        return new ConfigurationLoader(configurationName);
    }

    public <T extends Configuration> T as(Class<T> instance) {
        String configurationPath = String.format("assertzilla/%s", configurationFile);
        ClassLoader classLoader = ConfigurationLoader.class.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream(configurationPath)) {
            return YAML.loadAs(inputStream, instance);
        } catch (Exception e) {
            throw new ConfigurationIOException(e);
        }
    }
}
