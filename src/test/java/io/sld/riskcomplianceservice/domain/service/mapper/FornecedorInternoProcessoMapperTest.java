package io.sld.riskcomplianceservice.domain.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import io.sld.riskcomplianceservice.domain.service.mapper.FornecedorInternoProcessoMapper;
import org.junit.jupiter.api.BeforeEach;

class FornecedorInternoProcessoMapperTest {

    private FornecedorInternoProcessoMapper fornecedorInternoProcessoMapper;

    @BeforeEach
    public void setUp() {
        fornecedorInternoProcessoMapper = new FornecedorInternoProcessoMapperImpl();
    }
}
