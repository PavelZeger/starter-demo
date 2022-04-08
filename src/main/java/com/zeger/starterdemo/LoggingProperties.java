package com.zeger.starterdemo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * @author Pavel Zeger
 * @implNote starter-demo
 * @since 08/04/2022
 */
@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "demo.logging")
public class LoggingProperties {

    /**
     * Message prefix for logging message.
     */
    @NotBlank
    @Size(min = 4, max = 25)
    private String messagePrefix = "Logging Starter => ";

    @Valid
    private Payload payload = new Payload();

    @Getter
    @Setter
    public static class Payload {

        /**
         * Payload maximum length.
         */
        @Positive
        @Max(100)
        private int maxLength = 100;

    }

}
