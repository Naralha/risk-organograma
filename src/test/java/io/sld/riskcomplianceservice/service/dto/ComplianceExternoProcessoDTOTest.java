package io.sld.riskcomplianceservice.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.sld.riskcomplianceservice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ComplianceExternoProcessoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ComplianceExternoProcessoDTO.class);
        ComplianceExternoProcessoDTO complianceExternoProcessoDTO1 = new ComplianceExternoProcessoDTO();
        complianceExternoProcessoDTO1.setId(1L);
        ComplianceExternoProcessoDTO complianceExternoProcessoDTO2 = new ComplianceExternoProcessoDTO();
        assertThat(complianceExternoProcessoDTO1).isNotEqualTo(complianceExternoProcessoDTO2);
        complianceExternoProcessoDTO2.setId(complianceExternoProcessoDTO1.getId());
        assertThat(complianceExternoProcessoDTO1).isEqualTo(complianceExternoProcessoDTO2);
        complianceExternoProcessoDTO2.setId(2L);
        assertThat(complianceExternoProcessoDTO1).isNotEqualTo(complianceExternoProcessoDTO2);
        complianceExternoProcessoDTO1.setId(null);
        assertThat(complianceExternoProcessoDTO1).isNotEqualTo(complianceExternoProcessoDTO2);
    }
}
