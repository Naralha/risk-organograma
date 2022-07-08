package io.sld.riskcomplianceservice.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.sld.riskcomplianceservice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClienteInternoProcessoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClienteInternoProcessoDTO.class);
        ClienteInternoProcessoDTO clienteInternoProcessoDTO1 = new ClienteInternoProcessoDTO();
        clienteInternoProcessoDTO1.setId(1L);
        ClienteInternoProcessoDTO clienteInternoProcessoDTO2 = new ClienteInternoProcessoDTO();
        assertThat(clienteInternoProcessoDTO1).isNotEqualTo(clienteInternoProcessoDTO2);
        clienteInternoProcessoDTO2.setId(clienteInternoProcessoDTO1.getId());
        assertThat(clienteInternoProcessoDTO1).isEqualTo(clienteInternoProcessoDTO2);
        clienteInternoProcessoDTO2.setId(2L);
        assertThat(clienteInternoProcessoDTO1).isNotEqualTo(clienteInternoProcessoDTO2);
        clienteInternoProcessoDTO1.setId(null);
        assertThat(clienteInternoProcessoDTO1).isNotEqualTo(clienteInternoProcessoDTO2);
    }
}
