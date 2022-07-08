package io.sld.riskcomplianceservice.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FuncionarioOrganogramaMapperTest {

    private FuncionarioOrganogramaMapper funcionarioOrganogramaMapper;

    @BeforeEach
    public void setUp() {
        funcionarioOrganogramaMapper = new FuncionarioOrganogramaMapperImpl();
    }
}
