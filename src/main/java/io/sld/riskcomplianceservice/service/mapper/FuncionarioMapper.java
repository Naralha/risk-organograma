package io.sld.riskcomplianceservice.service.mapper;

import io.sld.riskcomplianceservice.domain.Empresa;
import io.sld.riskcomplianceservice.domain.Funcionario;
import io.sld.riskcomplianceservice.domain.Usuario;
import io.sld.riskcomplianceservice.service.dto.EmpresaDTO;
import io.sld.riskcomplianceservice.service.dto.FuncionarioDTO;
import io.sld.riskcomplianceservice.service.dto.UsuarioDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Funcionario} and its DTO {@link FuncionarioDTO}.
 */
@Mapper(componentModel = "spring")
public interface FuncionarioMapper extends EntityMapper<FuncionarioDTO, Funcionario> {
    @Mapping(target = "empresa", source = "empresa", qualifiedByName = "empresaId")
    @Mapping(target = "usuario", source = "usuario", qualifiedByName = "usuarioId")
    FuncionarioDTO toDto(Funcionario s);

    @Named("empresaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmpresaDTO toDtoEmpresaId(Empresa empresa);

    @Named("usuarioId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UsuarioDTO toDtoUsuarioId(Usuario usuario);
}
