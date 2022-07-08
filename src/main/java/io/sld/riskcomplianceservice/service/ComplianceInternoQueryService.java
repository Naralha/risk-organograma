package io.sld.riskcomplianceservice.service;

import io.sld.riskcomplianceservice.domain.*; // for static metamodels
import io.sld.riskcomplianceservice.domain.ComplianceInterno;
import io.sld.riskcomplianceservice.repository.ComplianceInternoRepository;
import io.sld.riskcomplianceservice.service.criteria.ComplianceInternoCriteria;
import io.sld.riskcomplianceservice.service.dto.ComplianceInternoDTO;
import io.sld.riskcomplianceservice.service.mapper.ComplianceInternoMapper;
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
 * Service for executing complex queries for {@link ComplianceInterno} entities in the database.
 * The main input is a {@link ComplianceInternoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ComplianceInternoDTO} or a {@link Page} of {@link ComplianceInternoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ComplianceInternoQueryService extends QueryService<ComplianceInterno> {

    private final Logger log = LoggerFactory.getLogger(ComplianceInternoQueryService.class);

    private final ComplianceInternoRepository complianceInternoRepository;

    private final ComplianceInternoMapper complianceInternoMapper;

    public ComplianceInternoQueryService(
        ComplianceInternoRepository complianceInternoRepository,
        ComplianceInternoMapper complianceInternoMapper
    ) {
        this.complianceInternoRepository = complianceInternoRepository;
        this.complianceInternoMapper = complianceInternoMapper;
    }

    /**
     * Return a {@link List} of {@link ComplianceInternoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ComplianceInternoDTO> findByCriteria(ComplianceInternoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ComplianceInterno> specification = createSpecification(criteria);
        return complianceInternoMapper.toDto(complianceInternoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ComplianceInternoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ComplianceInternoDTO> findByCriteria(ComplianceInternoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ComplianceInterno> specification = createSpecification(criteria);
        return complianceInternoRepository.findAll(specification, page).map(complianceInternoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ComplianceInternoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ComplianceInterno> specification = createSpecification(criteria);
        return complianceInternoRepository.count(specification);
    }

    /**
     * Function to convert {@link ComplianceInternoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ComplianceInterno> createSpecification(ComplianceInternoCriteria criteria) {
        Specification<ComplianceInterno> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ComplianceInterno_.id));
            }
            if (criteria.getIdnVarComplianteInterno() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getIdnVarComplianteInterno(), ComplianceInterno_.idnVarComplianteInterno)
                    );
            }
            if (criteria.getnVarNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getnVarNome(), ComplianceInterno_.nVarNome));
            }
            if (criteria.getnVarDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getnVarDescricao(), ComplianceInterno_.nVarDescricao));
            }
            if (criteria.getIdnVarEmpresa() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdnVarEmpresa(), ComplianceInterno_.idnVarEmpresa));
            }
            if (criteria.getIdnvarUsuario() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdnvarUsuario(), ComplianceInterno_.idnvarUsuario));
            }
            if (criteria.getComplianceInternoProcessoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getComplianceInternoProcessoId(),
                            root ->
                                root.join(ComplianceInterno_.complianceInternoProcessos, JoinType.LEFT).get(ComplianceInternoProcesso_.id)
                        )
                    );
            }
            if (criteria.getEmpresaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmpresaId(),
                            root -> root.join(ComplianceInterno_.empresa, JoinType.LEFT).get(Empresa_.id)
                        )
                    );
            }
            if (criteria.getUsuarioId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUsuarioId(),
                            root -> root.join(ComplianceInterno_.usuario, JoinType.LEFT).get(Usuario_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
