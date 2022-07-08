package io.sld.riskcomplianceservice.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FornecedorExternoProcessoMapperTest {

    private FornecedorExternoProcessoMapper fornecedorExternoProcessoMapper;

    @BeforeEach
    public void setUp() {
        fornecedorExternoProcessoMapper = new FornecedorExternoProcessoMapperImpl();
    }
}
