package io.sld.riskcomplianceservice.service.mapper;

import io.sld.riskcomplianceservice.domain.Funcionario;
import io.sld.riskcomplianceservice.domain.FuncionarioOrganograma;
import io.sld.riskcomplianceservice.domain.Organograma;
import io.sld.riskcomplianceservice.domain.Usuario;
import io.sld.riskcomplianceservice.service.dto.FuncionarioDTO;
import io.sld.riskcomplianceservice.service.dto.FuncionarioOrganogramaDTO;
import io.sld.riskcomplianceservice.service.dto.OrganogramaDTO;
import io.sld.riskcomplianceservice.service.dto.UsuarioDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FuncionarioOrganograma} and its DTO {@link FuncionarioOrganogramaDTO}.
 */
@Mapper(componentModel = "spring")
public interface FuncionarioOrganogramaMapper extends EntityMapper<FuncionarioOrganogramaDTO, FuncionarioOrganograma> {
    @Mapping(target = "funcionario", source = "funcionario", qualifiedByName = "funcionarioId")
    @Mapping(target = "organograma", source = "organograma", qualifiedByName = "organogramaId")
    @Mapping(target = "usuario", source = "usuario", qualifiedByName = "usuarioId")
    FuncionarioOrganogramaDTO toDto(FuncionarioOrganograma s);

    @Named("funcionarioId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FuncionarioDTO toDtoFuncionarioId(Funcionario funcionario);

    @Named("organogramaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrganogramaDTO toDtoOrganogramaId(Organograma organograma);

    @Named("usuarioId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UsuarioDTO toDtoUsuarioId(Usuario usuario);
}
