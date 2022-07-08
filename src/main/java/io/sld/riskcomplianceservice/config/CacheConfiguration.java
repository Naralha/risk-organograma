package io.sld.riskcomplianceservice.config;

import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration =
            Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder
                    .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                    .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                    .build()
            );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, io.sld.riskcomplianceservice.domain.ClienteExterno.class.getName());
            createCache(cm, io.sld.riskcomplianceservice.domain.ClienteExterno.class.getName() + ".clienteExternoProcessos");
            createCache(cm, io.sld.riskcomplianceservice.domain.ClienteExternoProcesso.class.getName());
            createCache(cm, io.sld.riskcomplianceservice.domain.ClienteInternoProcesso.class.getName());
            createCache(cm, io.sld.riskcomplianceservice.domain.ComplianceExterno.class.getName());
            createCache(cm, io.sld.riskcomplianceservice.domain.ComplianceExterno.class.getName() + ".complianceExternoProcessos");
            createCache(cm, io.sld.riskcomplianceservice.domain.ComplianceExternoProcesso.class.getName());
            createCache(cm, io.sld.riskcomplianceservice.domain.ComplianceInterno.class.getName());
            createCache(cm, io.sld.riskcomplianceservice.domain.ComplianceInterno.class.getName() + ".complianceInternoProcessos");
            createCache(cm, io.sld.riskcomplianceservice.domain.ComplianceInternoProcesso.class.getName());
            createCache(cm, io.sld.riskcomplianceservice.domain.Empresa.class.getName());
            createCache(cm, io.sld.riskcomplianceservice.domain.Empresa.class.getName() + ".clienteExternos");
            createCache(cm, io.sld.riskcomplianceservice.domain.Empresa.class.getName() + ".fornecedorExternos");
            createCache(cm, io.sld.riskcomplianceservice.domain.Empresa.class.getName() + ".complianceExternos");
            createCache(cm, io.sld.riskcomplianceservice.domain.Empresa.class.getName() + ".complianceInternos");
            createCache(cm, io.sld.riskcomplianceservice.domain.Empresa.class.getName() + ".funcionarios");
            createCache(cm, io.sld.riskcomplianceservice.domain.Empresa.class.getName() + ".macroProcessos");
            createCache(cm, io.sld.riskcomplianceservice.domain.Empresa.class.getName() + ".macroProcessoOrganogramas");
            createCache(cm, io.sld.riskcomplianceservice.domain.Empresa.class.getName() + ".organogramas");
            createCache(cm, io.sld.riskcomplianceservice.domain.Empresa.class.getName() + ".processos");
            createCache(cm, io.sld.riskcomplianceservice.domain.Empresa.class.getName() + ".usuarios");
            createCache(cm, io.sld.riskcomplianceservice.domain.FornecedorExterno.class.getName());
            createCache(cm, io.sld.riskcomplianceservice.domain.FornecedorExterno.class.getName() + ".fornecedorExternoProcessos");
            createCache(cm, io.sld.riskcomplianceservice.domain.FornecedorExternoProcesso.class.getName());
            createCache(cm, io.sld.riskcomplianceservice.domain.FornecedorInternoProcesso.class.getName());
            createCache(cm, io.sld.riskcomplianceservice.domain.Funcionario.class.getName());
            createCache(cm, io.sld.riskcomplianceservice.domain.Funcionario.class.getName() + ".funcionarioOrganogramas");
            createCache(cm, io.sld.riskcomplianceservice.domain.FuncionarioOrganograma.class.getName());
            createCache(cm, io.sld.riskcomplianceservice.domain.MacroProcesso.class.getName());
            createCache(cm, io.sld.riskcomplianceservice.domain.MacroProcesso.class.getName() + ".macroProcessoOrganogramas");
            createCache(cm, io.sld.riskcomplianceservice.domain.MacroProcessoOrganograma.class.getName());
            createCache(cm, io.sld.riskcomplianceservice.domain.Organograma.class.getName());
            createCache(cm, io.sld.riskcomplianceservice.domain.Organograma.class.getName() + ".macroProcessoOrganogramas");
            createCache(cm, io.sld.riskcomplianceservice.domain.Organograma.class.getName() + ".clienteInternoProcessos");
            createCache(cm, io.sld.riskcomplianceservice.domain.Organograma.class.getName() + ".funcionarioOrganogramas");
            createCache(cm, io.sld.riskcomplianceservice.domain.Organograma.class.getName() + ".fornecedorInternoProcessos");
            createCache(cm, io.sld.riskcomplianceservice.domain.Processo.class.getName());
            createCache(cm, io.sld.riskcomplianceservice.domain.Processo.class.getName() + ".clienteExternoProcessos");
            createCache(cm, io.sld.riskcomplianceservice.domain.Processo.class.getName() + ".complianceExternoProcessos");
            createCache(cm, io.sld.riskcomplianceservice.domain.Processo.class.getName() + ".fornecedorExternoProcessos");
            createCache(cm, io.sld.riskcomplianceservice.domain.Processo.class.getName() + ".clienteInternoProcessos");
            createCache(cm, io.sld.riskcomplianceservice.domain.Processo.class.getName() + ".complianceInternoProcessos");
            createCache(cm, io.sld.riskcomplianceservice.domain.Processo.class.getName() + ".fornecedorInternoProcessos");
            createCache(cm, io.sld.riskcomplianceservice.domain.Usuario.class.getName());
            createCache(cm, io.sld.riskcomplianceservice.domain.Usuario.class.getName() + ".clienteExternos");
            createCache(cm, io.sld.riskcomplianceservice.domain.Usuario.class.getName() + ".fornecedorExternos");
            createCache(cm, io.sld.riskcomplianceservice.domain.Usuario.class.getName() + ".complianceExternos");
            createCache(cm, io.sld.riskcomplianceservice.domain.Usuario.class.getName() + ".complianceInternos");
            createCache(cm, io.sld.riskcomplianceservice.domain.Usuario.class.getName() + ".funcionarios");
            createCache(cm, io.sld.riskcomplianceservice.domain.Usuario.class.getName() + ".macroProcessos");
            createCache(cm, io.sld.riskcomplianceservice.domain.Usuario.class.getName() + ".macroProcessoOrganogramas");
            createCache(cm, io.sld.riskcomplianceservice.domain.Usuario.class.getName() + ".organogramas");
            createCache(cm, io.sld.riskcomplianceservice.domain.Usuario.class.getName() + ".processos");
            createCache(cm, io.sld.riskcomplianceservice.domain.Usuario.class.getName() + ".clienteExternoProcessos");
            createCache(cm, io.sld.riskcomplianceservice.domain.Usuario.class.getName() + ".complianceExternoProcessos");
            createCache(cm, io.sld.riskcomplianceservice.domain.Usuario.class.getName() + ".fornecedorExternoProcessos");
            createCache(cm, io.sld.riskcomplianceservice.domain.Usuario.class.getName() + ".clienteInternoProcessos");
            createCache(cm, io.sld.riskcomplianceservice.domain.Usuario.class.getName() + ".funcionarioOrganogramas");
            createCache(cm, io.sld.riskcomplianceservice.domain.Usuario.class.getName() + ".complianceInternoProcessos");
            createCache(cm, io.sld.riskcomplianceservice.domain.Usuario.class.getName() + ".fornecedorInternoProcessos");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
