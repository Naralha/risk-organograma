package io.sld.riskcomplianceservice.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.sld.riskcomplianceservice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MacroProcessoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MacroProcesso.class);
        MacroProcesso macroProcesso1 = new MacroProcesso();
        macroProcesso1.setId(1L);
        MacroProcesso macroProcesso2 = new MacroProcesso();
        macroProcesso2.setId(macroProcesso1.getId());
        assertThat(macroProcesso1).isEqualTo(macroProcesso2);
        macroProcesso2.setId(2L);
        assertThat(macroProcesso1).isNotEqualTo(macroProcesso2);
        macroProcesso1.setId(null);
        assertThat(macroProcesso1).isNotEqualTo(macroProcesso2);
    }
}
