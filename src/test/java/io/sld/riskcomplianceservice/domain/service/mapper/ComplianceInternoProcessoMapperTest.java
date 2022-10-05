package io.sld.riskcomplianceservice.domain.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import io.sld.riskcomplianceservice.domain.service.mapper.ComplianceInternoProcessoMapper;
import org.junit.jupiter.api.BeforeEach;

class ComplianceInternoProcessoMapperTest {

    private ComplianceInternoProcessoMapper complianceInternoProcessoMapper;

    @BeforeEach
    public void setUp() {
        complianceInternoProcessoMapper = new ComplianceInternoProcessoMapperImpl();
    }
}
