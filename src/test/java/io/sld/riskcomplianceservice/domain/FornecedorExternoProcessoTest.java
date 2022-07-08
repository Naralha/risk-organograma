package io.sld.riskcomplianceservice.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.sld.riskcomplianceservice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FornecedorExternoProcessoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FornecedorExternoProcesso.class);
        FornecedorExternoProcesso fornecedorExternoProcesso1 = new FornecedorExternoProcesso();
        fornecedorExternoProcesso1.setId(1L);
        FornecedorExternoProcesso fornecedorExternoProcesso2 = new FornecedorExternoProcesso();
        fornecedorExternoProcesso2.setId(fornecedorExternoProcesso1.getId());
        assertThat(fornecedorExternoProcesso1).isEqualTo(fornecedorExternoProcesso2);
        fornecedorExternoProcesso2.setId(2L);
        assertThat(fornecedorExternoProcesso1).isNotEqualTo(fornecedorExternoProcesso2);
        fornecedorExternoProcesso1.setId(null);
        assertThat(fornecedorExternoProcesso1).isNotEqualTo(fornecedorExternoProcesso2);
    }
}
