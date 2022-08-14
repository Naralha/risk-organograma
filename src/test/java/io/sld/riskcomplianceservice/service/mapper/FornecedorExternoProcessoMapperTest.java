package io.sld.riskcomplianceservice.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import io.sld.riskcomplianceservice.domain.service.mapper.FornecedorExternoProcessoMapper;
import org.junit.jupiter.api.BeforeEach;

class FornecedorExternoProcessoMapperTest {

    private FornecedorExternoProcessoMapper fornecedorExternoProcessoMapper;

    @BeforeEach
    public void setUp() {
        fornecedorExternoProcessoMapper = new FornecedorExternoProcessoMapperImpl();
    }
}
