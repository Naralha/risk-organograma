package io.sld.riskcomplianceservice.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MacroProcessoMapperTest {

    private MacroProcessoMapper macroProcessoMapper;

    @BeforeEach
    public void setUp() {
        macroProcessoMapper = new MacroProcessoMapperImpl();
    }
}
