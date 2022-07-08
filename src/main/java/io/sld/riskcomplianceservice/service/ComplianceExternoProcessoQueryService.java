package io.sld.riskcomplianceservice.service;

import io.sld.riskcomplianceservice.domain.*; // for static metamodels
import io.sld.riskcomplianceservice.domain.ComplianceExternoProcesso;
import io.sld.riskcomplianceservice.repository.ComplianceExternoProcessoRepository;
import io.sld.riskcomplianceservice.service.criteria.ComplianceExternoProcessoCriteria;
import io.sld.riskcomplianceservice.service.dto.ComplianceExternoProcessoDTO;
import io.sld.riskcomplianceservice.service.mapper.ComplianceExternoProcessoMapper;
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
 * Service for executing complex queries for {@link ComplianceExternoProcesso} entities in the database.
 * The main input is a {@link ComplianceExternoProcessoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ComplianceExternoProcessoDTO} or a {@link Page} of {@link ComplianceExternoProcessoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ComplianceExternoProcessoQueryService extends QueryService<ComplianceExternoProcesso> {

    private final Logger log = LoggerFactory.getLogger(ComplianceExternoProcessoQueryService.class);

    private final ComplianceExternoProcessoRepository complianceExternoProcessoRepository;

    private final ComplianceExternoProcessoMapper complianceExternoProcessoMapper;

    public ComplianceExternoProcessoQueryService(
        ComplianceExternoProcessoRepository complianceExternoProcessoRepository,
        ComplianceExternoProcessoMapper complianceExternoProcessoMapper
    ) {
        this.complianceExternoProcessoRepository = complianceExternoProcessoRepository;
        this.complianceExternoProcessoMapper = complianceExternoProcessoMapper;
    }

    /**
     * Return a {@link List} of {@link ComplianceExternoProcessoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ComplianceExternoProcessoDTO> findByCriteria(ComplianceExternoProcessoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ComplianceExternoProcesso> specification = createSpecification(criteria);
        return complianceExternoProcessoMapper.toDto(complianceExternoProcessoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ComplianceExternoProcessoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ComplianceExternoProcessoDTO> findByCriteria(ComplianceExternoProcessoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ComplianceExternoProcesso> specification = createSpecification(criteria);
        return complianceExternoProcessoRepository.findAll(specification, page).map(complianceExternoProcessoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ComplianceExternoProcessoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ComplianceExternoProcesso> specification = createSpecification(criteria);
        return complianceExternoProcessoRepository.count(specification);
    }

    /**
     * Function to convert {@link ComplianceExternoProcessoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ComplianceExternoProcesso> createSpecification(ComplianceExternoProcessoCriteria criteria) {
        Specification<ComplianceExternoProcesso> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ComplianceExternoProcesso_.id));
            }
            if (criteria.getIdnVarComplianceExterno() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getIdnVarComplianceExterno(), ComplianceExternoProcesso_.idnVarComplianceExterno)
                    );
            }
            if (criteria.getIdnVarProcesso() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getIdnVarProcesso(), ComplianceExternoProcesso_.idnVarProcesso));
            }
            if (criteria.getIdnvarUsuario() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getIdnvarUsuario(), ComplianceExternoProcesso_.idnvarUsuario));
            }
            if (criteria.getComplianceExternoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getComplianceExternoId(),
                            root -> root.join(ComplianceExternoProcesso_.complianceExterno, JoinType.LEFT).get(ComplianceExterno_.id)
                        )
                    );
            }
            if (criteria.getProcessoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProcessoId(),
                            root -> root.join(ComplianceExternoProcesso_.processo, JoinType.LEFT).get(Processo_.id)
                        )
                    );
            }
            if (criteria.getUsuarioId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUsuarioId(),
                            root -> root.join(ComplianceExternoProcesso_.usuario, JoinType.LEFT).get(Usuario_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
