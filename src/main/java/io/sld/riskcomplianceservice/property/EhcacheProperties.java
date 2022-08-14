package io.sld.riskcomplianceservice.property;

import lombok.Getter;
import lombok.Setter;

/**
 * Stores application.yml 'application.cache.ehcache' properties
 */
@Getter
@Setter
public class EhcacheProperties {

    private Long timeToLiveSeconds;
    private Long maxEntries;

}
