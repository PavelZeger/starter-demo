package com.zeger.starterdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import static org.apache.commons.lang3.StringUtils.EMPTY;

@Slf4j
@EnableConfigurationProperties(LoggingProperties.class)
@ConditionalOnProperty(
        value = {"demo.logging.enabled"},
        havingValue = "true",
        matchIfMissing = true
)
public class LoggingAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public CommonsRequestLoggingFilter requestLoggingFilter(LoggingProperties loggingProperties) {
        CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
        final String messagePrefix = loggingProperties.getMessagePrefix();
        final int maxLength = loggingProperties.getPayload().getMaxLength();
        loggingFilter.setBeforeMessagePrefix(messagePrefix);
        loggingFilter.setAfterMessagePrefix(messagePrefix);
        loggingFilter.setBeforeMessageSuffix(EMPTY);
        loggingFilter.setAfterMessageSuffix(EMPTY);
        loggingFilter.setIncludePayload(true);
        loggingFilter.setMaxPayloadLength(maxLength);
        log.info("Logging configuration: message prefix [{}], max payload length [{}]", messagePrefix, maxLength);
        return loggingFilter;
    }

}
