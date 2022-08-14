package io.sld.riskcomplianceservice.config;


import io.sld.riskcomplianceservice.property.SwaggerProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

/**
 * Configuration class for 'openapi' and 'swagger-ui'
 */
@Configuration
@EnableConfigurationProperties({SwaggerProperties.class})
public class OpenApiConfiguration {

    /**
     * OpenAPI bean, used to configure swagger.
     *
     * @param swaggerProperties application.yml 'swagger' properties
     */
    @Bean
    public OpenAPI api(SwaggerProperties swaggerProperties) {
        return new OpenAPI().info(apiInfo(swaggerProperties))
                .externalDocs(anyNonBlank(swaggerProperties.getExternalDocumentation(), swaggerProperties.getExternalDocumentation())
                        ? new ExternalDocumentation().description(swaggerProperties.getExternalDocumentation()).url(swaggerProperties.getExternalDocumentation())
                        :null);
    }

    private Info apiInfo(SwaggerProperties swaggerProperties) {
        return new Info().title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .version(swaggerProperties.getVersion())
                .termsOfService(swaggerProperties.getTermsOfServiceUrl())
                .contact(anyNonBlank(swaggerProperties.getContactName(), swaggerProperties.getContactUrl(), swaggerProperties.getContactEmail())
                        ? new Contact().name(swaggerProperties.getContactName()).url(swaggerProperties.getContactUrl()).email(swaggerProperties.getContactEmail())
                        : null)
                .license(anyNonBlank(swaggerProperties.getLicense(), swaggerProperties.getLicenseUrl())
                        ? new License().name(swaggerProperties.getLicense()).url(swaggerProperties.getLicenseUrl())
                        :null);
    }

    private boolean anyNonBlank(String... values) {
        return !StringUtils.isAllBlank(values);
    }

}
