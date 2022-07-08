package io.sld.riskcomplianceservice.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.sld.riskcomplianceservice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MacroProcessoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MacroProcessoDTO.class);
        MacroProcessoDTO macroProcessoDTO1 = new MacroProcessoDTO();
        macroProcessoDTO1.setId(1L);
        MacroProcessoDTO macroProcessoDTO2 = new MacroProcessoDTO();
        assertThat(macroProcessoDTO1).isNotEqualTo(macroProcessoDTO2);
        macroProcessoDTO2.setId(macroProcessoDTO1.getId());
        assertThat(macroProcessoDTO1).isEqualTo(macroProcessoDTO2);
        macroProcessoDTO2.setId(2L);
        assertThat(macroProcessoDTO1).isNotEqualTo(macroProcessoDTO2);
        macroProcessoDTO1.setId(null);
        assertThat(macroProcessoDTO1).isNotEqualTo(macroProcessoDTO2);
    }
}
