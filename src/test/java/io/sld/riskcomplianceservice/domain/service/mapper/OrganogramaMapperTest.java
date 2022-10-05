package io.sld.riskcomplianceservice.domain.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import io.sld.riskcomplianceservice.domain.service.mapper.OrganogramaMapper;
import org.junit.jupiter.api.BeforeEach;

class OrganogramaMapperTest {

    private OrganogramaMapper organogramaMapper;

    @BeforeEach
    public void setUp() {
        organogramaMapper = new OrganogramaMapperImpl();
    }
}
