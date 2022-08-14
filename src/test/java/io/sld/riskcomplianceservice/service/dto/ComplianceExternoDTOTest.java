package io.sld.riskcomplianceservice.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.sld.riskcomplianceservice.domain.service.dto.ComplianceExternoDTO;
import io.sld.riskcomplianceservice.resource.TestUtil;
import org.junit.jupiter.api.Test;

class ComplianceExternoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ComplianceExternoDTO.class);
        ComplianceExternoDTO complianceExternoDTO1 = new ComplianceExternoDTO();
        complianceExternoDTO1.setId(1L);
        ComplianceExternoDTO complianceExternoDTO2 = new ComplianceExternoDTO();
        assertThat(complianceExternoDTO1).isNotEqualTo(complianceExternoDTO2);
        complianceExternoDTO2.setId(complianceExternoDTO1.getId());
        assertThat(complianceExternoDTO1).isEqualTo(complianceExternoDTO2);
        complianceExternoDTO2.setId(2L);
        assertThat(complianceExternoDTO1).isNotEqualTo(complianceExternoDTO2);
        complianceExternoDTO1.setId(null);
        assertThat(complianceExternoDTO1).isNotEqualTo(complianceExternoDTO2);
    }
}
