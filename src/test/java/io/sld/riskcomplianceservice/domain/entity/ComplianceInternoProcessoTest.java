package io.sld.riskcomplianceservice.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import io.sld.riskcomplianceservice.domain.entity.ComplianceInternoProcesso;
import io.sld.riskcomplianceservice.TestUtil;
import org.junit.jupiter.api.Test;

class ComplianceInternoProcessoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ComplianceInternoProcesso.class);
        ComplianceInternoProcesso complianceInternoProcesso1 = new ComplianceInternoProcesso();
        complianceInternoProcesso1.setId(1L);
        ComplianceInternoProcesso complianceInternoProcesso2 = new ComplianceInternoProcesso();
        complianceInternoProcesso2.setId(complianceInternoProcesso1.getId());
        assertThat(complianceInternoProcesso1).isEqualTo(complianceInternoProcesso2);
        complianceInternoProcesso2.setId(2L);
        assertThat(complianceInternoProcesso1).isNotEqualTo(complianceInternoProcesso2);
        complianceInternoProcesso1.setId(null);
        assertThat(complianceInternoProcesso1).isNotEqualTo(complianceInternoProcesso2);
    }
}
