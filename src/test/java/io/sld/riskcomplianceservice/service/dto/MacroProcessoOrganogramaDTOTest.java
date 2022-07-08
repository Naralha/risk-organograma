package io.sld.riskcomplianceservice.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.sld.riskcomplianceservice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MacroProcessoOrganogramaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MacroProcessoOrganogramaDTO.class);
        MacroProcessoOrganogramaDTO macroProcessoOrganogramaDTO1 = new MacroProcessoOrganogramaDTO();
        macroProcessoOrganogramaDTO1.setId(1L);
        MacroProcessoOrganogramaDTO macroProcessoOrganogramaDTO2 = new MacroProcessoOrganogramaDTO();
        assertThat(macroProcessoOrganogramaDTO1).isNotEqualTo(macroProcessoOrganogramaDTO2);
        macroProcessoOrganogramaDTO2.setId(macroProcessoOrganogramaDTO1.getId());
        assertThat(macroProcessoOrganogramaDTO1).isEqualTo(macroProcessoOrganogramaDTO2);
        macroProcessoOrganogramaDTO2.setId(2L);
        assertThat(macroProcessoOrganogramaDTO1).isNotEqualTo(macroProcessoOrganogramaDTO2);
        macroProcessoOrganogramaDTO1.setId(null);
        assertThat(macroProcessoOrganogramaDTO1).isNotEqualTo(macroProcessoOrganogramaDTO2);
    }
}
