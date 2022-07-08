package io.sld.riskcomplianceservice.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ComplianceInternoProcessoMapperTest {

    private ComplianceInternoProcessoMapper complianceInternoProcessoMapper;

    @BeforeEach
    public void setUp() {
        complianceInternoProcessoMapper = new ComplianceInternoProcessoMapperImpl();
    }
}
