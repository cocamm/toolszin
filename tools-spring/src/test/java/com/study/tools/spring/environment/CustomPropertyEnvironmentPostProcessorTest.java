package com.study.tools.spring.environment;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.test.context.support.TestPropertySourceUtils;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;

class CustomPropertyEnvironmentPostProcessorTest {

    private CustomPropertyEnvironmentPostProcessor processor = new CustomPropertyEnvironmentPostProcessor();;
    private ConfigurableEnvironment environment = new StandardEnvironment();;

    @Test
    void testCustomProperty() {
        assertThat(this.environment.resolvePlaceholders("${my.custom.key:}"), is(emptyOrNullString()));

        TestPropertySourceUtils.addInlinedPropertiesToEnvironment(this.environment, "spring.custom.property=true");
        this.processor.postProcessEnvironment(environment, null);

        assertThat(this.environment.resolvePlaceholders("${my.custom.key:}"), is("custom-value"));
    }

    @Test
    void testEmptyCustomProperty() {
        assertThat(this.environment.resolvePlaceholders("${my.custom.key:}"), is(emptyOrNullString()));

        TestPropertySourceUtils.addInlinedPropertiesToEnvironment(this.environment, "spring.custom.property=false");
        this.processor.postProcessEnvironment(environment, null);

        assertThat(this.environment.resolvePlaceholders("${my.custom.key:}"), is(emptyOrNullString()));
    }

    @AfterEach
    void setUp() {
        this.environment.getSystemProperties().remove("my.custom.key");
    }

}