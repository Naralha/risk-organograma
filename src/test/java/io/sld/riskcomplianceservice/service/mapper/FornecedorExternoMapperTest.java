package io.sld.riskcomplianceservice.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FornecedorExternoMapperTest {

    private FornecedorExternoMapper fornecedorExternoMapper;

    @BeforeEach
    public void setUp() {
        fornecedorExternoMapper = new FornecedorExternoMapperImpl();
    }
}
