package io.sld.riskcomplianceservice.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FuncionarioMapperTest {

    private FuncionarioMapper funcionarioMapper;

    @BeforeEach
    public void setUp() {
        funcionarioMapper = new FuncionarioMapperImpl();
    }
}
