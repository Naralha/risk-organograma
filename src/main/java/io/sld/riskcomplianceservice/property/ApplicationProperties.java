package io.sld.riskcomplianceservice.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * Stores application.yaml 'application' properties
 */
@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String version;

    private LoggingProperties logging;

    private CacheProperties cache;

}
