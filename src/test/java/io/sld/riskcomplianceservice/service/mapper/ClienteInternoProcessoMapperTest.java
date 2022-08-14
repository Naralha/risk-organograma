package io.sld.riskcomplianceservice.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import io.sld.riskcomplianceservice.domain.service.mapper.ClienteInternoProcessoMapper;
import org.junit.jupiter.api.BeforeEach;

class ClienteInternoProcessoMapperTest {

    private ClienteInternoProcessoMapper clienteInternoProcessoMapper;

    @BeforeEach
    public void setUp() {
        clienteInternoProcessoMapper = new ClienteInternoProcessoMapperImpl();
    }
}
