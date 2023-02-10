package io.sld.riskcomplianceservice.domain.service.mapper;

import io.sld.riskcomplianceservice.domain.entity.Organograma;
import io.sld.riskcomplianceservice.domain.service.dto.OrganogramaArrayDTO;
import io.sld.riskcomplianceservice.domain.service.dto.OrganogramaDTO;

import java.util.List;

public class OrganogramaArrayMapper {

    OrganogramaDTO toDTO(OrganogramaArrayDTO organogramaArrayDTO){
        OrganogramaDTO organogramaDTO = new OrganogramaDTO();
//        organogramaDTO.setNVarNome();
        return organogramaDTO;
    }
}
