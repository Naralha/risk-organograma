package io.sld.riskcomplianceservice.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import io.sld.riskcomplianceservice.domain.service.mapper.ComplianceInternoMapper;
import org.junit.jupiter.api.BeforeEach;

class ComplianceInternoMapperTest {

    private ComplianceInternoMapper complianceInternoMapper;

    @BeforeEach
    public void setUp() {
        complianceInternoMapper = new ComplianceInternoMapperImpl();
    }
}
