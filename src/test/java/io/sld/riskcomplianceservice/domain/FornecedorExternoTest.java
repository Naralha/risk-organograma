package io.sld.riskcomplianceservice.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.sld.riskcomplianceservice.domain.entity.FornecedorExterno;
import io.sld.riskcomplianceservice.resource.TestUtil;
import org.junit.jupiter.api.Test;

class FornecedorExternoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FornecedorExterno.class);
        FornecedorExterno fornecedorExterno1 = new FornecedorExterno();
        fornecedorExterno1.setId(1L);
        FornecedorExterno fornecedorExterno2 = new FornecedorExterno();
        fornecedorExterno2.setId(fornecedorExterno1.getId());
        assertThat(fornecedorExterno1).isEqualTo(fornecedorExterno2);
        fornecedorExterno2.setId(2L);
        assertThat(fornecedorExterno1).isNotEqualTo(fornecedorExterno2);
        fornecedorExterno1.setId(null);
        assertThat(fornecedorExterno1).isNotEqualTo(fornecedorExterno2);
    }
}
