package io.sld.riskcomplianceservice.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import io.sld.riskcomplianceservice.domain.service.mapper.FuncionarioMapper;
import org.junit.jupiter.api.BeforeEach;

class FuncionarioMapperTest {

    private FuncionarioMapper funcionarioMapper;

    @BeforeEach
    public void setUp() {
        funcionarioMapper = new FuncionarioMapperImpl();
    }
}
