package io.sld.riskcomplianceservice.domain.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import io.sld.riskcomplianceservice.domain.service.mapper.ComplianceExternoProcessoMapper;
import org.junit.jupiter.api.BeforeEach;

class ComplianceExternoProcessoMapperTest {

    private ComplianceExternoProcessoMapper complianceExternoProcessoMapper;

    @BeforeEach
    public void setUp() {
        complianceExternoProcessoMapper = new ComplianceExternoProcessoMapperImpl();
    }
}
