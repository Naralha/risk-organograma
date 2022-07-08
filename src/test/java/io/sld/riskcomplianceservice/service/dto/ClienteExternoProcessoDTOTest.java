package io.sld.riskcomplianceservice.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.sld.riskcomplianceservice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClienteExternoProcessoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClienteExternoProcessoDTO.class);
        ClienteExternoProcessoDTO clienteExternoProcessoDTO1 = new ClienteExternoProcessoDTO();
        clienteExternoProcessoDTO1.setId(1L);
        ClienteExternoProcessoDTO clienteExternoProcessoDTO2 = new ClienteExternoProcessoDTO();
        assertThat(clienteExternoProcessoDTO1).isNotEqualTo(clienteExternoProcessoDTO2);
        clienteExternoProcessoDTO2.setId(clienteExternoProcessoDTO1.getId());
        assertThat(clienteExternoProcessoDTO1).isEqualTo(clienteExternoProcessoDTO2);
        clienteExternoProcessoDTO2.setId(2L);
        assertThat(clienteExternoProcessoDTO1).isNotEqualTo(clienteExternoProcessoDTO2);
        clienteExternoProcessoDTO1.setId(null);
        assertThat(clienteExternoProcessoDTO1).isNotEqualTo(clienteExternoProcessoDTO2);
    }
}
