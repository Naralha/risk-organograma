package io.sld.riskcomplianceservice.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import io.sld.riskcomplianceservice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClienteExternoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClienteExternoDTO.class);
        ClienteExternoDTO clienteExternoDTO1 = new ClienteExternoDTO();
        clienteExternoDTO1.setId(1L);
        ClienteExternoDTO clienteExternoDTO2 = new ClienteExternoDTO();
        assertThat(clienteExternoDTO1).isNotEqualTo(clienteExternoDTO2);
        clienteExternoDTO2.setId(clienteExternoDTO1.getId());
        assertThat(clienteExternoDTO1).isEqualTo(clienteExternoDTO2);
        clienteExternoDTO2.setId(2L);
        assertThat(clienteExternoDTO1).isNotEqualTo(clienteExternoDTO2);
        clienteExternoDTO1.setId(null);
        assertThat(clienteExternoDTO1).isNotEqualTo(clienteExternoDTO2);
    }
}
