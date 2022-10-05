package io.sld.riskcomplianceservice.domain.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import io.sld.riskcomplianceservice.domain.service.mapper.ClienteExternoMapper;
import org.junit.jupiter.api.BeforeEach;

class ClienteExternoMapperTest {

    private ClienteExternoMapper clienteExternoMapper;

    @BeforeEach
    public void setUp() {
        clienteExternoMapper = new ClienteExternoMapperImpl();
    }
}
