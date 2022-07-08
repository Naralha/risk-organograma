package io.sld.riskcomplianceservice.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.sld.riskcomplianceservice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClienteInternoProcessoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClienteInternoProcesso.class);
        ClienteInternoProcesso clienteInternoProcesso1 = new ClienteInternoProcesso();
        clienteInternoProcesso1.setId(1L);
        ClienteInternoProcesso clienteInternoProcesso2 = new ClienteInternoProcesso();
        clienteInternoProcesso2.setId(clienteInternoProcesso1.getId());
        assertThat(clienteInternoProcesso1).isEqualTo(clienteInternoProcesso2);
        clienteInternoProcesso2.setId(2L);
        assertThat(clienteInternoProcesso1).isNotEqualTo(clienteInternoProcesso2);
        clienteInternoProcesso1.setId(null);
        assertThat(clienteInternoProcesso1).isNotEqualTo(clienteInternoProcesso2);
    }
}
