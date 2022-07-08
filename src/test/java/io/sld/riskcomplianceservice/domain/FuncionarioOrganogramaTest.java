package io.sld.riskcomplianceservice.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.sld.riskcomplianceservice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FuncionarioOrganogramaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FuncionarioOrganograma.class);
        FuncionarioOrganograma funcionarioOrganograma1 = new FuncionarioOrganograma();
        funcionarioOrganograma1.setId(1L);
        FuncionarioOrganograma funcionarioOrganograma2 = new FuncionarioOrganograma();
        funcionarioOrganograma2.setId(funcionarioOrganograma1.getId());
        assertThat(funcionarioOrganograma1).isEqualTo(funcionarioOrganograma2);
        funcionarioOrganograma2.setId(2L);
        assertThat(funcionarioOrganograma1).isNotEqualTo(funcionarioOrganograma2);
        funcionarioOrganograma1.setId(null);
        assertThat(funcionarioOrganograma1).isNotEqualTo(funcionarioOrganograma2);
    }
}
