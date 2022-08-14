package io.sld.riskcomplianceservice.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.sld.riskcomplianceservice.domain.service.dto.OrganogramaDTO;
import io.sld.riskcomplianceservice.resource.TestUtil;
import org.junit.jupiter.api.Test;

class OrganogramaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrganogramaDTO.class);
        OrganogramaDTO organogramaDTO1 = new OrganogramaDTO();
        organogramaDTO1.setId(1L);
        OrganogramaDTO organogramaDTO2 = new OrganogramaDTO();
        assertThat(organogramaDTO1).isNotEqualTo(organogramaDTO2);
        organogramaDTO2.setId(organogramaDTO1.getId());
        assertThat(organogramaDTO1).isEqualTo(organogramaDTO2);
        organogramaDTO2.setId(2L);
        assertThat(organogramaDTO1).isNotEqualTo(organogramaDTO2);
        organogramaDTO1.setId(null);
        assertThat(organogramaDTO1).isNotEqualTo(organogramaDTO2);
    }
}
