package io.sld.riskcomplianceservice.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.sld.riskcomplianceservice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ComplianceInternoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ComplianceInternoDTO.class);
        ComplianceInternoDTO complianceInternoDTO1 = new ComplianceInternoDTO();
        complianceInternoDTO1.setId(1L);
        ComplianceInternoDTO complianceInternoDTO2 = new ComplianceInternoDTO();
        assertThat(complianceInternoDTO1).isNotEqualTo(complianceInternoDTO2);
        complianceInternoDTO2.setId(complianceInternoDTO1.getId());
        assertThat(complianceInternoDTO1).isEqualTo(complianceInternoDTO2);
        complianceInternoDTO2.setId(2L);
        assertThat(complianceInternoDTO1).isNotEqualTo(complianceInternoDTO2);
        complianceInternoDTO1.setId(null);
        assertThat(complianceInternoDTO1).isNotEqualTo(complianceInternoDTO2);
    }
}
