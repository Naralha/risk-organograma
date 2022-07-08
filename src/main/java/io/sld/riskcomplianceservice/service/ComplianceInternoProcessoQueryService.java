package io.sld.riskcomplianceservice.service;

import io.sld.riskcomplianceservice.domain.*; // for static metamodels
import io.sld.riskcomplianceservice.domain.ComplianceInternoProcesso;
import io.sld.riskcomplianceservice.repository.ComplianceInternoProcessoRepository;
import io.sld.riskcomplianceservice.service.criteria.ComplianceInternoProcessoCriteria;
import io.sld.riskcomplianceservice.service.dto.ComplianceInternoProcessoDTO;
import io.sld.riskcomplianceservice.service.mapper.ComplianceInternoProcessoMapper;
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
 * Service for executing complex queries for {@link ComplianceInternoProcesso} entities in the database.
 * The main input is a {@link ComplianceInternoProcessoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ComplianceInternoProcessoDTO} or a {@link Page} of {@link ComplianceInternoProcessoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ComplianceInternoProcessoQueryService extends QueryService<ComplianceInternoProcesso> {

    private final Logger log = LoggerFactory.getLogger(ComplianceInternoProcessoQueryService.class);

    private final ComplianceInternoProcessoRepository complianceInternoProcessoRepository;

    private final ComplianceInternoProcessoMapper complianceInternoProcessoMapper;

    public ComplianceInternoProcessoQueryService(
        ComplianceInternoProcessoRepository complianceInternoProcessoRepository,
        ComplianceInternoProcessoMapper complianceInternoProcessoMapper
    ) {
        this.complianceInternoProcessoRepository = complianceInternoProcessoRepository;
        this.complianceInternoProcessoMapper = complianceInternoProcessoMapper;
    }

    /**
     * Return a {@link List} of {@link ComplianceInternoProcessoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ComplianceInternoProcessoDTO> findByCriteria(ComplianceInternoProcessoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ComplianceInternoProcesso> specification = createSpecification(criteria);
        return complianceInternoProcessoMapper.toDto(complianceInternoProcessoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ComplianceInternoProcessoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ComplianceInternoProcessoDTO> findByCriteria(ComplianceInternoProcessoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ComplianceInternoProcesso> specification = createSpecification(criteria);
        return complianceInternoProcessoRepository.findAll(specification, page).map(complianceInternoProcessoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ComplianceInternoProcessoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ComplianceInternoProcesso> specification = createSpecification(criteria);
        return complianceInternoProcessoRepository.count(specification);
    }

    /**
     * Function to convert {@link ComplianceInternoProcessoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ComplianceInternoProcesso> createSpecification(ComplianceInternoProcessoCriteria criteria) {
        Specification<ComplianceInternoProcesso> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ComplianceInternoProcesso_.id));
            }
            if (criteria.getIdnVarComplianceInterno() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getIdnVarComplianceInterno(), ComplianceInternoProcesso_.idnVarComplianceInterno)
                    );
            }
            if (criteria.getIdnVarProcesso() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getIdnVarProcesso(), ComplianceInternoProcesso_.idnVarProcesso));
            }
            if (criteria.getIdnvarUsuario() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getIdnvarUsuario(), ComplianceInternoProcesso_.idnvarUsuario));
            }
            if (criteria.getComplianceInternoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getComplianceInternoId(),
                            root -> root.join(ComplianceInternoProcesso_.complianceInterno, JoinType.LEFT).get(ComplianceInterno_.id)
                        )
                    );
            }
            if (criteria.getProcessoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProcessoId(),
                            root -> root.join(ComplianceInternoProcesso_.processo, JoinType.LEFT).get(Processo_.id)
                        )
                    );
            }
            if (criteria.getUsuarioId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUsuarioId(),
                            root -> root.join(ComplianceInternoProcesso_.usuario, JoinType.LEFT).get(Usuario_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
