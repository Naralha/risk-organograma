package io.sld.riskcomplianceservice.domain.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import io.sld.riskcomplianceservice.domain.service.mapper.MacroProcessoOrganogramaMapper;
import org.junit.jupiter.api.BeforeEach;

class MacroProcessoOrganogramaMapperTest {

    private MacroProcessoOrganogramaMapper macroProcessoOrganogramaMapper;

    @BeforeEach
    public void setUp() {
        macroProcessoOrganogramaMapper = new MacroProcessoOrganogramaMapperImpl();
    }
}
