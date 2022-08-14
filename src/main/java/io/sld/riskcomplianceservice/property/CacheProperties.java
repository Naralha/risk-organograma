package io.sld.riskcomplianceservice.property;

import lombok.Getter;
import lombok.Setter;

/**
 * Stores application.yml 'application.cache' properties
 */
@Getter
@Setter
public class CacheProperties {

    private EhcacheProperties ehcache;

}
