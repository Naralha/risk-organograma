package io.sld.riskcomplianceservice.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.sld.riskcomplianceservice.domain.entity.Organograma;
import io.sld.riskcomplianceservice.resource.TestUtil;
import org.junit.jupiter.api.Test;

class OrganogramaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Organograma.class);
        Organograma organograma1 = new Organograma();
        organograma1.setId(1L);
        Organograma organograma2 = new Organograma();
        organograma2.setId(organograma1.getId());
        assertThat(organograma1).isEqualTo(organograma2);
        organograma2.setId(2L);
        assertThat(organograma1).isNotEqualTo(organograma2);
        organograma1.setId(null);
        assertThat(organograma1).isNotEqualTo(organograma2);
    }
}
