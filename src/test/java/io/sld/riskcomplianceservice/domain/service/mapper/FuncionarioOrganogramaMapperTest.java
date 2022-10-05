package io.sld.riskcomplianceservice.domain.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import io.sld.riskcomplianceservice.domain.service.mapper.FuncionarioOrganogramaMapper;
import org.junit.jupiter.api.BeforeEach;

class FuncionarioOrganogramaMapperTest {

    private FuncionarioOrganogramaMapper funcionarioOrganogramaMapper;

    @BeforeEach
    public void setUp() {
        funcionarioOrganogramaMapper = new FuncionarioOrganogramaMapperImpl();
    }
}
