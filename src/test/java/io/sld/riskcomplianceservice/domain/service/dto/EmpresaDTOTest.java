package io.sld.riskcomplianceservice.domain.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.sld.riskcomplianceservice.domain.service.dto.EmpresaDTO;
import io.sld.riskcomplianceservice.TestUtil;
import org.junit.jupiter.api.Test;

class EmpresaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmpresaDTO.class);
        EmpresaDTO empresaDTO1 = new EmpresaDTO();
        empresaDTO1.setId(1L);
        EmpresaDTO empresaDTO2 = new EmpresaDTO();
        assertThat(empresaDTO1).isNotEqualTo(empresaDTO2);
        empresaDTO2.setId(empresaDTO1.getId());
        assertThat(empresaDTO1).isEqualTo(empresaDTO2);
        empresaDTO2.setId(2L);
        assertThat(empresaDTO1).isNotEqualTo(empresaDTO2);
        empresaDTO1.setId(null);
        assertThat(empresaDTO1).isNotEqualTo(empresaDTO2);
    }
}
