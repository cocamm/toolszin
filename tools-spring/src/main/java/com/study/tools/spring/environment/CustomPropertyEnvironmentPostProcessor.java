package com.study.tools.spring.environment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

import static org.springframework.core.env.StandardEnvironment.SYSTEM_PROPERTIES_PROPERTY_SOURCE_NAME;

public class CustomPropertyEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private static final String CUSTOM_PROPERTY = "spring.custom.property";
    private static final String DEFAULT_PROPERTIES = "defaultProperties";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String customPropertyValue = environment.getProperty(CUSTOM_PROPERTY, "false");
        if (Boolean.parseBoolean(customPropertyValue)) {
            addCustomProperty(environment.getPropertySources());
        }
    }

    private void addCustomProperty(MutablePropertySources propertySources) {
        if (propertySources.contains(SYSTEM_PROPERTIES_PROPERTY_SOURCE_NAME)) {
            PropertySource<?> source = propertySources.get(SYSTEM_PROPERTIES_PROPERTY_SOURCE_NAME);
            if (source instanceof MapPropertySource) {
                ((MapPropertySource) source).getSource().put("my.custom.key", "custom-value");
            }
        }
    }
}
