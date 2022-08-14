package io.sld.riskcomplianceservice.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import io.sld.riskcomplianceservice.domain.service.mapper.ProcessoMapper;
import org.junit.jupiter.api.BeforeEach;

class ProcessoMapperTest {

    private ProcessoMapper processoMapper;

    @BeforeEach
    public void setUp() {
        processoMapper = new ProcessoMapperImpl();
    }
}
