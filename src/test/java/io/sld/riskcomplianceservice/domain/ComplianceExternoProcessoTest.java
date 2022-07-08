package io.sld.riskcomplianceservice.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.sld.riskcomplianceservice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ComplianceExternoProcessoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ComplianceExternoProcesso.class);
        ComplianceExternoProcesso complianceExternoProcesso1 = new ComplianceExternoProcesso();
        complianceExternoProcesso1.setId(1L);
        ComplianceExternoProcesso complianceExternoProcesso2 = new ComplianceExternoProcesso();
        complianceExternoProcesso2.setId(complianceExternoProcesso1.getId());
        assertThat(complianceExternoProcesso1).isEqualTo(complianceExternoProcesso2);
        complianceExternoProcesso2.setId(2L);
        assertThat(complianceExternoProcesso1).isNotEqualTo(complianceExternoProcesso2);
        complianceExternoProcesso1.setId(null);
        assertThat(complianceExternoProcesso1).isNotEqualTo(complianceExternoProcesso2);
    }
}
