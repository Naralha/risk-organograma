package io.sld.riskcomplianceservice.domain;

import static org.assertj.core.api.Assertions.assertThat;

import io.sld.riskcomplianceservice.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClienteExternoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClienteExterno.class);
        ClienteExterno clienteExterno1 = new ClienteExterno();
        clienteExterno1.setId(1L);
        ClienteExterno clienteExterno2 = new ClienteExterno();
        clienteExterno2.setId(clienteExterno1.getId());
        assertThat(clienteExterno1).isEqualTo(clienteExterno2);
        clienteExterno2.setId(2L);
        assertThat(clienteExterno1).isNotEqualTo(clienteExterno2);
        clienteExterno1.setId(null);
        assertThat(clienteExterno1).isNotEqualTo(clienteExterno2);
    }
}
