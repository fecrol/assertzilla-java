package com.github.fecrol.assertzilla.core;

import com.github.fecrol.assertzilla.core.exceptions.ConfigurationIOException;
import com.github.fecrol.assertzilla.core.models.EmptyExistingConfigurationPojo;
import com.github.fecrol.assertzilla.core.models.HelloWorldConfiguration;
import com.github.fecrol.assertzilla.core.models.NonExistentConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DisplayName("When loading configurations with the ConfigurationLoader class")
public class ConfigurationLoaderTests {

    @Test
    @DisplayName("Any existent configuration should be loaded successfully")
    void itShouldSuccessfullyLoadExistentConfigurations() {
        // Given
        String configurationFileName = "existent-configuration.yml";
        // When
        Configuration configuration = ConfigurationLoader
                .loadResource(configurationFileName)
                .as(HelloWorldConfiguration.class);
        // Then
        assertThat(configuration, is(notNullValue()));
        assertThat(configuration, is(instanceOf(HelloWorldConfiguration.class)));
    }

    @Test
    @DisplayName("Configuration IO Exception should be thrown for non existent configuration files")
    void itThrowsConfigurationIOExceptionForNonExistentConfigurationFiles() {
        // Given
        String configurationFileName = "non-existent-configuration.yml";
        // Then
        assertThatThrownBy(() -> ConfigurationLoader.loadResource(configurationFileName).as(NonExistentConfiguration.class))
                .isInstanceOf(ConfigurationIOException.class);
    }

    @Test
    @DisplayName("Configuration IO Exception should be thrown for existent configuration files with missing fields in target POJO")
    void itThrowsConfigurationIOExceptionForExistentConfigurationFilesWithMissingFieldsInPojo() {
        // Given
        String configurationFileName = "existent-configuration.yml";
        // Then
        assertThatThrownBy(() -> ConfigurationLoader.loadResource(configurationFileName).as(EmptyExistingConfigurationPojo.class))
                .isInstanceOf(ConfigurationIOException.class);
    }
}
