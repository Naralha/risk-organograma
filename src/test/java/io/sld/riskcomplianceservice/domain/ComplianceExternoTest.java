package io.sld.riskcomplianceservice.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.sld.riskcomplianceservice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ComplianceExternoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ComplianceExterno.class);
        ComplianceExterno complianceExterno1 = new ComplianceExterno();
        complianceExterno1.setId(1L);
        ComplianceExterno complianceExterno2 = new ComplianceExterno();
        complianceExterno2.setId(complianceExterno1.getId());
        assertThat(complianceExterno1).isEqualTo(complianceExterno2);
        complianceExterno2.setId(2L);
        assertThat(complianceExterno1).isNotEqualTo(complianceExterno2);
        complianceExterno1.setId(null);
        assertThat(complianceExterno1).isNotEqualTo(complianceExterno2);
    }
}
