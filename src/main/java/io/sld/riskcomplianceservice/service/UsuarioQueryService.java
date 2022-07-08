package io.sld.riskcomplianceservice.service;

import io.sld.riskcomplianceservice.domain.*; // for static metamodels
import io.sld.riskcomplianceservice.domain.Usuario;
import io.sld.riskcomplianceservice.repository.UsuarioRepository;
import io.sld.riskcomplianceservice.service.criteria.UsuarioCriteria;
import io.sld.riskcomplianceservice.service.dto.UsuarioDTO;
import io.sld.riskcomplianceservice.service.mapper.UsuarioMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Usuario} entities in the database.
 * The main input is a {@link UsuarioCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UsuarioDTO} or a {@link Page} of {@link UsuarioDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UsuarioQueryService extends QueryService<Usuario> {

    private final Logger log = LoggerFactory.getLogger(UsuarioQueryService.class);

    private final UsuarioRepository usuarioRepository;

    private final UsuarioMapper usuarioMapper;

    public UsuarioQueryService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    /**
     * Return a {@link List} of {@link UsuarioDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UsuarioDTO> findByCriteria(UsuarioCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Usuario> specification = createSpecification(criteria);
        return usuarioMapper.toDto(usuarioRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link UsuarioDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UsuarioDTO> findByCriteria(UsuarioCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Usuario> specification = createSpecification(criteria);
        return usuarioRepository.findAll(specification, page).map(usuarioMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UsuarioCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Usuario> specification = createSpecification(criteria);
        return usuarioRepository.count(specification);
    }

    /**
     * Function to convert {@link UsuarioCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Usuario> createSpecification(UsuarioCriteria criteria) {
        Specification<Usuario> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Usuario_.id));
            }
            if (criteria.getIdnVarUsuario() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdnVarUsuario(), Usuario_.idnVarUsuario));
            }
            if (criteria.getnVarNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getnVarNome(), Usuario_.nVarNome));
            }
            if (criteria.getIdnVarEmpresa() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdnVarEmpresa(), Usuario_.idnVarEmpresa));
            }
            if (criteria.getIdnVarUsuarioCadastro() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getIdnVarUsuarioCadastro(), Usuario_.idnVarUsuarioCadastro));
            }
            if (criteria.getnVarSenha() != null) {
                specification = specification.and(buildStringSpecification(criteria.getnVarSenha(), Usuario_.nVarSenha));
            }
            if (criteria.getClienteExternoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getClienteExternoId(),
                            root -> root.join(Usuario_.clienteExternos, JoinType.LEFT).get(ClienteExterno_.id)
                        )
                    );
            }
            if (criteria.getFornecedorExternoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getFornecedorExternoId(),
                            root -> root.join(Usuario_.fornecedorExternos, JoinType.LEFT).get(FornecedorExterno_.id)
                        )
                    );
            }
            if (criteria.getComplianceExternoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getComplianceExternoId(),
                            root -> root.join(Usuario_.complianceExternos, JoinType.LEFT).get(ComplianceExterno_.id)
                        )
                    );
            }
            if (criteria.getComplianceInternoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getComplianceInternoId(),
                            root -> root.join(Usuario_.complianceInternos, JoinType.LEFT).get(ComplianceInterno_.id)
                        )
                    );
            }
            if (criteria.getFuncionarioId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getFuncionarioId(),
                            root -> root.join(Usuario_.funcionarios, JoinType.LEFT).get(Funcionario_.id)
                        )
                    );
            }
            if (criteria.getMacroProcessoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMacroProcessoId(),
                            root -> root.join(Usuario_.macroProcessos, JoinType.LEFT).get(MacroProcesso_.id)
                        )
                    );
            }
            if (criteria.getMacroProcessoOrganogramaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMacroProcessoOrganogramaId(),
                            root -> root.join(Usuario_.macroProcessoOrganogramas, JoinType.LEFT).get(MacroProcessoOrganograma_.id)
                        )
                    );
            }
            if (criteria.getOrganogramaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrganogramaId(),
                            root -> root.join(Usuario_.organogramas, JoinType.LEFT).get(Organograma_.id)
                        )
                    );
            }
            if (criteria.getProcessoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getProcessoId(), root -> root.join(Usuario_.processos, JoinType.LEFT).get(Processo_.id))
                    );
            }
            if (criteria.getClienteExternoProcessoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getClienteExternoProcessoId(),
                            root -> root.join(Usuario_.clienteExternoProcessos, JoinType.LEFT).get(ClienteExternoProcesso_.id)
                        )
                    );
            }
            if (criteria.getComplianceExternoProcessoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getComplianceExternoProcessoId(),
                            root -> root.join(Usuario_.complianceExternoProcessos, JoinType.LEFT).get(ComplianceExternoProcesso_.id)
                        )
                    );
            }
            if (criteria.getFornecedorExternoProcessoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getFornecedorExternoProcessoId(),
                            root -> root.join(Usuario_.fornecedorExternoProcessos, JoinType.LEFT).get(FornecedorExternoProcesso_.id)
                        )
                    );
            }
            if (criteria.getClienteInternoProcessoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getClienteInternoProcessoId(),
                            root -> root.join(Usuario_.clienteInternoProcessos, JoinType.LEFT).get(ClienteInternoProcesso_.id)
                        )
                    );
            }
            if (criteria.getFuncionarioOrganogramaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getFuncionarioOrganogramaId(),
                            root -> root.join(Usuario_.funcionarioOrganogramas, JoinType.LEFT).get(FuncionarioOrganograma_.id)
                        )
                    );
            }
            if (criteria.getComplianceInternoProcessoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getComplianceInternoProcessoId(),
                            root -> root.join(Usuario_.complianceInternoProcessos, JoinType.LEFT).get(ComplianceInternoProcesso_.id)
                        )
                    );
            }
            if (criteria.getFornecedorInternoProcessoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getFornecedorInternoProcessoId(),
                            root -> root.join(Usuario_.fornecedorInternoProcessos, JoinType.LEFT).get(FornecedorInternoProcesso_.id)
                        )
                    );
            }
            if (criteria.getEmpresaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getEmpresaId(), root -> root.join(Usuario_.empresa, JoinType.LEFT).get(Empresa_.id))
                    );
            }
        }
        return specification;
    }
}
