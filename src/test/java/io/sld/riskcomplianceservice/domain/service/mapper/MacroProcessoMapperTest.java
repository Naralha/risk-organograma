package io.sld.riskcomplianceservice.domain.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import io.sld.riskcomplianceservice.domain.service.mapper.MacroProcessoMapper;
import org.junit.jupiter.api.BeforeEach;

class MacroProcessoMapperTest {

    private MacroProcessoMapper macroProcessoMapper;

    @BeforeEach
    public void setUp() {
        macroProcessoMapper = new MacroProcessoMapperImpl();
    }
}
