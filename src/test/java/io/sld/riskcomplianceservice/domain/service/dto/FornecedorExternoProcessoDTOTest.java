package io.sld.riskcomplianceservice.domain.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.sld.riskcomplianceservice.domain.service.dto.FornecedorExternoProcessoDTO;
import io.sld.riskcomplianceservice.TestUtil;
import org.junit.jupiter.api.Test;

class FornecedorExternoProcessoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FornecedorExternoProcessoDTO.class);
        FornecedorExternoProcessoDTO fornecedorExternoProcessoDTO1 = new FornecedorExternoProcessoDTO();
        fornecedorExternoProcessoDTO1.setId(1L);
        FornecedorExternoProcessoDTO fornecedorExternoProcessoDTO2 = new FornecedorExternoProcessoDTO();
        assertThat(fornecedorExternoProcessoDTO1).isNotEqualTo(fornecedorExternoProcessoDTO2);
        fornecedorExternoProcessoDTO2.setId(fornecedorExternoProcessoDTO1.getId());
        assertThat(fornecedorExternoProcessoDTO1).isEqualTo(fornecedorExternoProcessoDTO2);
        fornecedorExternoProcessoDTO2.setId(2L);
        assertThat(fornecedorExternoProcessoDTO1).isNotEqualTo(fornecedorExternoProcessoDTO2);
        fornecedorExternoProcessoDTO1.setId(null);
        assertThat(fornecedorExternoProcessoDTO1).isNotEqualTo(fornecedorExternoProcessoDTO2);
    }
}
