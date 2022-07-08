package io.sld.riskcomplianceservice.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ComplianceInternoMapperTest {

    private ComplianceInternoMapper complianceInternoMapper;

    @BeforeEach
    public void setUp() {
        complianceInternoMapper = new ComplianceInternoMapperImpl();
    }
}
