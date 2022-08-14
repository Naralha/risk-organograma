package io.sld.riskcomplianceservice.property;

import javax.validation.constraints.NotBlank;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

/**
 * Stores application.yaml 'swagger' properties
 */
@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "swagger", ignoreUnknownFields = false)
public class SwaggerProperties {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String version;

    private String termsOfServiceUrl;

    private String contactName;

    private String contactUrl;

    private String contactEmail;

    private String license;

    private String licenseUrl;

    private String externalDocumentation;

    private String externalDocumentationUrl;

}
