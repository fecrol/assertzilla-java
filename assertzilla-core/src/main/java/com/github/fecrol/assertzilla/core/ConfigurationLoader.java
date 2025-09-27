package com.github.fecrol.assertzilla.core;

import com.github.fecrol.assertzilla.core.exceptions.ConfigurationIOException;
import com.google.common.base.Strings;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

public class ConfigurationLoader {

    private Yaml YAML;
    private String node;
    private String configurationFile;

    private ConfigurationLoader(String configurationFile) {
        YAML = new Yaml();
        this.node = null;
        this.configurationFile = configurationFile;
    }

    public static ConfigurationLoader loadResource(String configurationName) {
        return new ConfigurationLoader(configurationName);
    }

    public ConfigurationLoader node(String node) {
        this.node = node;
        return this;
    }

    public <T extends Configuration> T as(Class<T> instance) {
        String configurationPath = String.format("assertzilla/%s", configurationFile);
        ClassLoader classLoader = ConfigurationLoader.class.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream(configurationPath)) {
            if (!Strings.isNullOrEmpty(node)) {
                Map<String, Object> root = YAML.load(inputStream);
                Object webDriverNode = root.get(node);
                return YAML.loadAs(YAML.dump(webDriverNode), instance);
            } else return YAML.loadAs(inputStream, instance);
        } catch (Exception e) {
            throw new ConfigurationIOException(e);
        }
    }
}
