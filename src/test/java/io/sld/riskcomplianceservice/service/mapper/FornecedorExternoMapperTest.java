package io.sld.riskcomplianceservice.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import io.sld.riskcomplianceservice.domain.service.mapper.FornecedorExternoMapper;
import org.junit.jupiter.api.BeforeEach;

class FornecedorExternoMapperTest {

    private FornecedorExternoMapper fornecedorExternoMapper;

    @BeforeEach
    public void setUp() {
        fornecedorExternoMapper = new FornecedorExternoMapperImpl();
    }
}
