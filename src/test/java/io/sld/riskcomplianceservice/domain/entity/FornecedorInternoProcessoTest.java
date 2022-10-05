package io.sld.riskcomplianceservice.domain.entity;

import static org.assertj.core.api.Assertions.assertThat;

import io.sld.riskcomplianceservice.domain.entity.FornecedorInternoProcesso;
import io.sld.riskcomplianceservice.TestUtil;
import org.junit.jupiter.api.Test;

class FornecedorInternoProcessoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FornecedorInternoProcesso.class);
        FornecedorInternoProcesso fornecedorInternoProcesso1 = new FornecedorInternoProcesso();
        fornecedorInternoProcesso1.setId(1L);
        FornecedorInternoProcesso fornecedorInternoProcesso2 = new FornecedorInternoProcesso();
        fornecedorInternoProcesso2.setId(fornecedorInternoProcesso1.getId());
        assertThat(fornecedorInternoProcesso1).isEqualTo(fornecedorInternoProcesso2);
        fornecedorInternoProcesso2.setId(2L);
        assertThat(fornecedorInternoProcesso1).isNotEqualTo(fornecedorInternoProcesso2);
        fornecedorInternoProcesso1.setId(null);
        assertThat(fornecedorInternoProcesso1).isNotEqualTo(fornecedorInternoProcesso2);
    }
}
