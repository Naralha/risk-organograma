package io.sld.riskcomplianceservice.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ComplianceExternoProcessoMapperTest {

    private ComplianceExternoProcessoMapper complianceExternoProcessoMapper;

    @BeforeEach
    public void setUp() {
        complianceExternoProcessoMapper = new ComplianceExternoProcessoMapperImpl();
    }
}
