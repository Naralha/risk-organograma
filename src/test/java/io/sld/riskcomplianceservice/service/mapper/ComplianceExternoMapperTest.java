package io.sld.riskcomplianceservice.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ComplianceExternoMapperTest {

    private ComplianceExternoMapper complianceExternoMapper;

    @BeforeEach
    public void setUp() {
        complianceExternoMapper = new ComplianceExternoMapperImpl();
    }
}
