package io.sld.riskcomplianceservice.domain.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.sld.riskcomplianceservice.domain.service.dto.FornecedorExternoDTO;
import io.sld.riskcomplianceservice.TestUtil;
import org.junit.jupiter.api.Test;

class FornecedorExternoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FornecedorExternoDTO.class);
        FornecedorExternoDTO fornecedorExternoDTO1 = new FornecedorExternoDTO();
        fornecedorExternoDTO1.setId(1L);
        FornecedorExternoDTO fornecedorExternoDTO2 = new FornecedorExternoDTO();
        assertThat(fornecedorExternoDTO1).isNotEqualTo(fornecedorExternoDTO2);
        fornecedorExternoDTO2.setId(fornecedorExternoDTO1.getId());
        assertThat(fornecedorExternoDTO1).isEqualTo(fornecedorExternoDTO2);
        fornecedorExternoDTO2.setId(2L);
        assertThat(fornecedorExternoDTO1).isNotEqualTo(fornecedorExternoDTO2);
        fornecedorExternoDTO1.setId(null);
        assertThat(fornecedorExternoDTO1).isNotEqualTo(fornecedorExternoDTO2);
    }
}
