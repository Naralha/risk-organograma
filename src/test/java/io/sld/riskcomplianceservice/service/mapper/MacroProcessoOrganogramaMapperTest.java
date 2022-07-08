package io.sld.riskcomplianceservice.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MacroProcessoOrganogramaMapperTest {

    private MacroProcessoOrganogramaMapper macroProcessoOrganogramaMapper;

    @BeforeEach
    public void setUp() {
        macroProcessoOrganogramaMapper = new MacroProcessoOrganogramaMapperImpl();
    }
}
