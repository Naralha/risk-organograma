package io.sld.riskcomplianceservice.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FornecedorInternoProcessoMapperTest {

    private FornecedorInternoProcessoMapper fornecedorInternoProcessoMapper;

    @BeforeEach
    public void setUp() {
        fornecedorInternoProcessoMapper = new FornecedorInternoProcessoMapperImpl();
    }
}
