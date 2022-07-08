package io.sld.riskcomplianceservice.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClienteExternoProcessoMapperTest {

    private ClienteExternoProcessoMapper clienteExternoProcessoMapper;

    @BeforeEach
    public void setUp() {
        clienteExternoProcessoMapper = new ClienteExternoProcessoMapperImpl();
    }
}
