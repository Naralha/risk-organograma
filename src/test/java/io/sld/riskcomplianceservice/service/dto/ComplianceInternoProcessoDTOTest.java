package io.sld.riskcomplianceservice.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.sld.riskcomplianceservice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ComplianceInternoProcessoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ComplianceInternoProcessoDTO.class);
        ComplianceInternoProcessoDTO complianceInternoProcessoDTO1 = new ComplianceInternoProcessoDTO();
        complianceInternoProcessoDTO1.setId(1L);
        ComplianceInternoProcessoDTO complianceInternoProcessoDTO2 = new ComplianceInternoProcessoDTO();
        assertThat(complianceInternoProcessoDTO1).isNotEqualTo(complianceInternoProcessoDTO2);
        complianceInternoProcessoDTO2.setId(complianceInternoProcessoDTO1.getId());
        assertThat(complianceInternoProcessoDTO1).isEqualTo(complianceInternoProcessoDTO2);
        complianceInternoProcessoDTO2.setId(2L);
        assertThat(complianceInternoProcessoDTO1).isNotEqualTo(complianceInternoProcessoDTO2);
        complianceInternoProcessoDTO1.setId(null);
        assertThat(complianceInternoProcessoDTO1).isNotEqualTo(complianceInternoProcessoDTO2);
    }
}
