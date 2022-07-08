package io.sld.riskcomplianceservice.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrganogramaMapperTest {

    private OrganogramaMapper organogramaMapper;

    @BeforeEach
    public void setUp() {
        organogramaMapper = new OrganogramaMapperImpl();
    }
}
