package com.zeger.starterdemo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.context.properties.bind.validation.BindValidationException;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Pavel Zeger
 * @implNote starter-demo
 * @since 08/04/2022
 */
class LoggingAutoConfigurationTest {

    @Test
    @DisplayName("Should auto-configuration be applied to a web application")
    void testRequestLoggingFilterSuccess() {
        new WebApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(LoggingAutoConfiguration.class))
                .run(context -> assertThat(context)
                        .hasNotFailed()
                        .hasSingleBean(CommonsRequestLoggingFilter.class)
                        .hasSingleBean(LoggingProperties.class)
                        .getBean(LoggingProperties.class).hasNoNullFieldsOrProperties()
                );
    }

    @Test
    @DisplayName("Should auto-configuration be failed due to validation constraints")
    void testRequestLoggingFilterFailure() {
        new WebApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(LoggingAutoConfiguration.class))
                .withPropertyValues("demo.logging.message-prefix=>>>")
                .run(context -> assertThat(context)
                        .hasFailed()
                        .getFailure()
                        .hasRootCauseExactlyInstanceOf(BindValidationException.class)
                        .hasStackTraceContaining("'demo.logging' on field 'messagePrefix'")
                );
    }

    @Test
    @DisplayName("Should logging filter be not applied to context")
    void testRequestLoggingFilterCustom() {
        new WebApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(LoggingAutoConfiguration.class))
                .withUserConfiguration(TestConfiguration.class)
                .run(context -> assertThat(context)
                        .hasNotFailed()
                        .hasSingleBean(CommonsRequestLoggingFilter.class)
                        .hasSingleBean(MyRequestLoggingFilter.class)
                );
    }

    private static class TestConfiguration {

        @Bean
        public MyRequestLoggingFilter myRequestLoggingFilter() {
            return new MyRequestLoggingFilter();
        }

    }

    private static class MyRequestLoggingFilter extends CommonsRequestLoggingFilter {

        @Override
        protected boolean shouldLog(HttpServletRequest request) {
            return true;
        }

    }

}