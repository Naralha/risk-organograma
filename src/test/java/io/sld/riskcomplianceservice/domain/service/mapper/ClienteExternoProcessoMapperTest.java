package io.sld.riskcomplianceservice.domain.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import io.sld.riskcomplianceservice.domain.service.mapper.ClienteExternoProcessoMapper;
import org.junit.jupiter.api.BeforeEach;

class ClienteExternoProcessoMapperTest {

    private ClienteExternoProcessoMapper clienteExternoProcessoMapper;

    @BeforeEach
    public void setUp() {
        clienteExternoProcessoMapper = new ClienteExternoProcessoMapperImpl();
    }
}
