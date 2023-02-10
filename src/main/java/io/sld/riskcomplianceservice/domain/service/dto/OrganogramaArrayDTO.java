package io.sld.riskcomplianceservice.domain.service.dto;

import io.sld.riskcomplianceservice.domain.entity.Empresa;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class OrganogramaArrayDTO {

    private Long id;
    private String name;
    private Empresa empresa;
    private String parentId;
    private List<OrganogramaArrayDTO> children;

    public OrganogramaArrayDTO(Long id, String name) {
        this.id = id;
        this.name = name;
        this.children = new ArrayList<>();
    }

}
