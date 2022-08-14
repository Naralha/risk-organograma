package io.sld.riskcomplianceservice.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import io.sld.riskcomplianceservice.domain.service.mapper.ComplianceExternoMapper;
import org.junit.jupiter.api.BeforeEach;

class ComplianceExternoMapperTest {

    private ComplianceExternoMapper complianceExternoMapper;

    @BeforeEach
    public void setUp() {
        complianceExternoMapper = new ComplianceExternoMapperImpl();
    }
}
