package io.sld.riskcomplianceservice.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.sld.riskcomplianceservice.domain.service.dto.FuncionarioOrganogramaDTO;
import io.sld.riskcomplianceservice.resource.TestUtil;
import org.junit.jupiter.api.Test;

class FuncionarioOrganogramaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FuncionarioOrganogramaDTO.class);
        FuncionarioOrganogramaDTO funcionarioOrganogramaDTO1 = new FuncionarioOrganogramaDTO();
        funcionarioOrganogramaDTO1.setId(1L);
        FuncionarioOrganogramaDTO funcionarioOrganogramaDTO2 = new FuncionarioOrganogramaDTO();
        assertThat(funcionarioOrganogramaDTO1).isNotEqualTo(funcionarioOrganogramaDTO2);
        funcionarioOrganogramaDTO2.setId(funcionarioOrganogramaDTO1.getId());
        assertThat(funcionarioOrganogramaDTO1).isEqualTo(funcionarioOrganogramaDTO2);
        funcionarioOrganogramaDTO2.setId(2L);
        assertThat(funcionarioOrganogramaDTO1).isNotEqualTo(funcionarioOrganogramaDTO2);
        funcionarioOrganogramaDTO1.setId(null);
        assertThat(funcionarioOrganogramaDTO1).isNotEqualTo(funcionarioOrganogramaDTO2);
    }
}
