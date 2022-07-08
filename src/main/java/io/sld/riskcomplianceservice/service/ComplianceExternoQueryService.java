package io.sld.riskcomplianceservice.service;

import io.sld.riskcomplianceservice.domain.*; // for static metamodels
import io.sld.riskcomplianceservice.domain.ComplianceExterno;
import io.sld.riskcomplianceservice.repository.ComplianceExternoRepository;
import io.sld.riskcomplianceservice.service.criteria.ComplianceExternoCriteria;
import io.sld.riskcomplianceservice.service.dto.ComplianceExternoDTO;
import io.sld.riskcomplianceservice.service.mapper.ComplianceExternoMapper;
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
 * Service for executing complex queries for {@link ComplianceExterno} entities in the database.
 * The main input is a {@link ComplianceExternoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ComplianceExternoDTO} or a {@link Page} of {@link ComplianceExternoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ComplianceExternoQueryService extends QueryService<ComplianceExterno> {

    private final Logger log = LoggerFactory.getLogger(ComplianceExternoQueryService.class);

    private final ComplianceExternoRepository complianceExternoRepository;

    private final ComplianceExternoMapper complianceExternoMapper;

    public ComplianceExternoQueryService(
        ComplianceExternoRepository complianceExternoRepository,
        ComplianceExternoMapper complianceExternoMapper
    ) {
        this.complianceExternoRepository = complianceExternoRepository;
        this.complianceExternoMapper = complianceExternoMapper;
    }

    /**
     * Return a {@link List} of {@link ComplianceExternoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ComplianceExternoDTO> findByCriteria(ComplianceExternoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ComplianceExterno> specification = createSpecification(criteria);
        return complianceExternoMapper.toDto(complianceExternoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ComplianceExternoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ComplianceExternoDTO> findByCriteria(ComplianceExternoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ComplianceExterno> specification = createSpecification(criteria);
        return complianceExternoRepository.findAll(specification, page).map(complianceExternoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ComplianceExternoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ComplianceExterno> specification = createSpecification(criteria);
        return complianceExternoRepository.count(specification);
    }

    /**
     * Function to convert {@link ComplianceExternoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ComplianceExterno> createSpecification(ComplianceExternoCriteria criteria) {
        Specification<ComplianceExterno> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ComplianceExterno_.id));
            }
            if (criteria.getIdnVarComplianceExterno() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getIdnVarComplianceExterno(), ComplianceExterno_.idnVarComplianceExterno)
                    );
            }
            if (criteria.getnVarNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getnVarNome(), ComplianceExterno_.nVarNome));
            }
            if (criteria.getnVarDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getnVarDescricao(), ComplianceExterno_.nVarDescricao));
            }
            if (criteria.getIdnVarEmpresa() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdnVarEmpresa(), ComplianceExterno_.idnVarEmpresa));
            }
            if (criteria.getIdnvarUsuario() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdnvarUsuario(), ComplianceExterno_.idnvarUsuario));
            }
            if (criteria.getComplianceExternoProcessoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getComplianceExternoProcessoId(),
                            root ->
                                root.join(ComplianceExterno_.complianceExternoProcessos, JoinType.LEFT).get(ComplianceExternoProcesso_.id)
                        )
                    );
            }
            if (criteria.getEmpresaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmpresaId(),
                            root -> root.join(ComplianceExterno_.empresa, JoinType.LEFT).get(Empresa_.id)
                        )
                    );
            }
            if (criteria.getUsuarioId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUsuarioId(),
                            root -> root.join(ComplianceExterno_.usuario, JoinType.LEFT).get(Usuario_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
