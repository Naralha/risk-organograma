package io.sld.riskcomplianceservice.domain.service;


import io.sld.riskcomplianceservice.domain.entity.*;
import io.sld.riskcomplianceservice.domain.repository.OrganogramaRepository;
import io.sld.riskcomplianceservice.domain.service.dto.OrganogramaDTO;
import io.sld.riskcomplianceservice.domain.service.criteria.OrganogramaCriteria;
import io.sld.riskcomplianceservice.domain.service.mapper.OrganogramaMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service for executing complex queries for {@link Organograma} entities in the database.
 * The main input is a {@link OrganogramaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OrganogramaDTO} or a {@link Page} of {@link OrganogramaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OrganogramaQueryService extends QueryService<Organograma> {

    private final Logger log = LoggerFactory.getLogger(OrganogramaQueryService.class);

    private final OrganogramaRepository organogramaRepository;

    private final OrganogramaMapper organogramaMapper;

    public OrganogramaQueryService(OrganogramaRepository organogramaRepository, OrganogramaMapper organogramaMapper) {
        this.organogramaRepository = organogramaRepository;
        this.organogramaMapper = organogramaMapper;
    }

    /**
     * Return a {@link List} of {@link OrganogramaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OrganogramaDTO> findByCriteria(OrganogramaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Organograma> specification = createSpecification(criteria);
        return organogramaMapper.toDto(organogramaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OrganogramaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OrganogramaDTO> findByCriteria(OrganogramaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Organograma> specification = createSpecification(criteria);
        return organogramaRepository.findAll(specification, page).map(organogramaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OrganogramaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Organograma> specification = createSpecification(criteria);
        return organogramaRepository.count(specification);
    }

    /**
     * Function to convert {@link OrganogramaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Organograma> createSpecification(OrganogramaCriteria criteria) {
        Specification<Organograma> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Organograma_.id));
            }
            if (criteria.getIdnVarOrganograma() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getIdnVarOrganograma(), Organograma_.idnVarOrganograma));
            }
            if (criteria.getIdnVarEmpresa() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdnVarEmpresa(), Organograma_.idnVarEmpresa));
            }
            if (criteria.getnVarNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getnVarNome(), Organograma_.nVarNome));
            }
            if (criteria.getnVarDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getnVarDescricao(), Organograma_.nVarDescricao));
            }
            if (criteria.getIdnVarPaiOrganograma() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getIdnVarPaiOrganograma(), Organograma_.idnVarPaiOrganograma));
            }
            if (criteria.getIdnvarUsuario() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdnvarUsuario(), Organograma_.idnvarUsuario));
            }
            if (criteria.getIdnVarRoofTop() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdnVarRoofTop(), Organograma_.idnVarRoofTop));
            }
            if (criteria.getMacroProcessoOrganogramaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMacroProcessoOrganogramaId(),
                            root -> root.join(Organograma_.macroProcessoOrganogramas, JoinType.LEFT).get(MacroProcessoOrganograma_.id)
                        )
                    );
            }
            if (criteria.getClienteInternoProcessoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getClienteInternoProcessoId(),
                            root -> root.join(Organograma_.clienteInternoProcessos, JoinType.LEFT).get(ClienteInternoProcesso_.id)
                        )
                    );
            }
            if (criteria.getFuncionarioOrganogramaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getFuncionarioOrganogramaId(),
                            root -> root.join(Organograma_.funcionarioOrganogramas, JoinType.LEFT).get(FuncionarioOrganograma_.id)
                        )
                    );
            }
            if (criteria.getFornecedorInternoProcessoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getFornecedorInternoProcessoId(),
                            root -> root.join(Organograma_.fornecedorInternoProcessos, JoinType.LEFT).get(FornecedorInternoProcesso_.id)
                        )
                    );
            }
            if (criteria.getEmpresaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getEmpresaId(), root -> root.join(Organograma_.empresa, JoinType.LEFT).get(Empresa_.id))
                    );
            }
            if (criteria.getUsuarioId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getUsuarioId(), root -> root.join(Organograma_.usuario, JoinType.LEFT).get(Usuario_.id))
                    );
            }
        }
        return specification;
    }
}
