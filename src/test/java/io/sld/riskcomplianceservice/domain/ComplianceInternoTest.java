package io.sld.riskcomplianceservice.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.sld.riskcomplianceservice.domain.entity.ComplianceInterno;
import io.sld.riskcomplianceservice.resource.TestUtil;
import org.junit.jupiter.api.Test;

class ComplianceInternoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ComplianceInterno.class);
        ComplianceInterno complianceInterno1 = new ComplianceInterno();
        complianceInterno1.setId(1L);
        ComplianceInterno complianceInterno2 = new ComplianceInterno();
        complianceInterno2.setId(complianceInterno1.getId());
        assertThat(complianceInterno1).isEqualTo(complianceInterno2);
        complianceInterno2.setId(2L);
        assertThat(complianceInterno1).isNotEqualTo(complianceInterno2);
        complianceInterno1.setId(null);
        assertThat(complianceInterno1).isNotEqualTo(complianceInterno2);
    }
}
