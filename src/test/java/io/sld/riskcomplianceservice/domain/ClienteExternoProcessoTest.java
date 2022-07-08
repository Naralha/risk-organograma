package io.sld.riskcomplianceservice.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.sld.riskcomplianceservice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClienteExternoProcessoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClienteExternoProcesso.class);
        ClienteExternoProcesso clienteExternoProcesso1 = new ClienteExternoProcesso();
        clienteExternoProcesso1.setId(1L);
        ClienteExternoProcesso clienteExternoProcesso2 = new ClienteExternoProcesso();
        clienteExternoProcesso2.setId(clienteExternoProcesso1.getId());
        assertThat(clienteExternoProcesso1).isEqualTo(clienteExternoProcesso2);
        clienteExternoProcesso2.setId(2L);
        assertThat(clienteExternoProcesso1).isNotEqualTo(clienteExternoProcesso2);
        clienteExternoProcesso1.setId(null);
        assertThat(clienteExternoProcesso1).isNotEqualTo(clienteExternoProcesso2);
    }
}
