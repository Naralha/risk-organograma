package io.sld.riskcomplianceservice.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.sld.riskcomplianceservice.domain.service.dto.FornecedorInternoProcessoDTO;
import io.sld.riskcomplianceservice.resource.TestUtil;
import org.junit.jupiter.api.Test;

class FornecedorInternoProcessoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FornecedorInternoProcessoDTO.class);
        FornecedorInternoProcessoDTO fornecedorInternoProcessoDTO1 = new FornecedorInternoProcessoDTO();
        fornecedorInternoProcessoDTO1.setId(1L);
        FornecedorInternoProcessoDTO fornecedorInternoProcessoDTO2 = new FornecedorInternoProcessoDTO();
        assertThat(fornecedorInternoProcessoDTO1).isNotEqualTo(fornecedorInternoProcessoDTO2);
        fornecedorInternoProcessoDTO2.setId(fornecedorInternoProcessoDTO1.getId());
        assertThat(fornecedorInternoProcessoDTO1).isEqualTo(fornecedorInternoProcessoDTO2);
        fornecedorInternoProcessoDTO2.setId(2L);
        assertThat(fornecedorInternoProcessoDTO1).isNotEqualTo(fornecedorInternoProcessoDTO2);
        fornecedorInternoProcessoDTO1.setId(null);
        assertThat(fornecedorInternoProcessoDTO1).isNotEqualTo(fornecedorInternoProcessoDTO2);
    }
}
