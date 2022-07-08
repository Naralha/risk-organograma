package io.sld.riskcomplianceservice.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClienteInternoProcessoMapperTest {

    private ClienteInternoProcessoMapper clienteInternoProcessoMapper;

    @BeforeEach
    public void setUp() {
        clienteInternoProcessoMapper = new ClienteInternoProcessoMapperImpl();
    }
}
