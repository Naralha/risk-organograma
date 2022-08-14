package io.sld.riskcomplianceservice.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configuration class for 'JPA'
 */
@Configuration
@EnableTransactionManagement
@EntityScan("io.sld.riskcomplianceservice.domain.entity")
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
@EnableJpaRepositories("io.sld.riskcomplianceservice.domain.repository")
public class JpaConfiguration {

}
