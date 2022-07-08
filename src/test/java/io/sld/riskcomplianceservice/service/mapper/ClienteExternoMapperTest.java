package io.sld.riskcomplianceservice.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClienteExternoMapperTest {

    private ClienteExternoMapper clienteExternoMapper;

    @BeforeEach
    public void setUp() {
        clienteExternoMapper = new ClienteExternoMapperImpl();
    }
}
