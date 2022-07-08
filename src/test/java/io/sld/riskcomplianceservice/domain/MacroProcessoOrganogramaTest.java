package io.sld.riskcomplianceservice.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.sld.riskcomplianceservice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MacroProcessoOrganogramaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MacroProcessoOrganograma.class);
        MacroProcessoOrganograma macroProcessoOrganograma1 = new MacroProcessoOrganograma();
        macroProcessoOrganograma1.setId(1L);
        MacroProcessoOrganograma macroProcessoOrganograma2 = new MacroProcessoOrganograma();
        macroProcessoOrganograma2.setId(macroProcessoOrganograma1.getId());
        assertThat(macroProcessoOrganograma1).isEqualTo(macroProcessoOrganograma2);
        macroProcessoOrganograma2.setId(2L);
        assertThat(macroProcessoOrganograma1).isNotEqualTo(macroProcessoOrganograma2);
        macroProcessoOrganograma1.setId(null);
        assertThat(macroProcessoOrganograma1).isNotEqualTo(macroProcessoOrganograma2);
    }
}
