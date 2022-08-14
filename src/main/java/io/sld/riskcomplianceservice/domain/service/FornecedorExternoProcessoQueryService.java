package io.sld.riskcomplianceservice.domain.service;


import io.sld.riskcomplianceservice.domain.entity.*;
import io.sld.riskcomplianceservice.domain.repository.FornecedorExternoProcessoRepository;
import io.sld.riskcomplianceservice.domain.service.dto.FornecedorExternoProcessoDTO;
import io.sld.riskcomplianceservice.domain.service.criteria.FornecedorExternoProcessoCriteria;
import io.sld.riskcomplianceservice.domain.service.mapper.FornecedorExternoProcessoMapper;
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
 * Service for executing complex queries for {@link FornecedorExternoProcesso} entities in the database.
 * The main input is a {@link FornecedorExternoProcessoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FornecedorExternoProcessoDTO} or a {@link Page} of {@link FornecedorExternoProcessoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FornecedorExternoProcessoQueryService extends QueryService<FornecedorExternoProcesso> {

    private final Logger log = LoggerFactory.getLogger(FornecedorExternoProcessoQueryService.class);

    private final FornecedorExternoProcessoRepository fornecedorExternoProcessoRepository;

    private final FornecedorExternoProcessoMapper fornecedorExternoProcessoMapper;

    public FornecedorExternoProcessoQueryService(
        FornecedorExternoProcessoRepository fornecedorExternoProcessoRepository,
        FornecedorExternoProcessoMapper fornecedorExternoProcessoMapper
    ) {
        this.fornecedorExternoProcessoRepository = fornecedorExternoProcessoRepository;
        this.fornecedorExternoProcessoMapper = fornecedorExternoProcessoMapper;
    }

    /**
     * Return a {@link List} of {@link FornecedorExternoProcessoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FornecedorExternoProcessoDTO> findByCriteria(FornecedorExternoProcessoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FornecedorExternoProcesso> specification = createSpecification(criteria);
        return fornecedorExternoProcessoMapper.toDto(fornecedorExternoProcessoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FornecedorExternoProcessoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FornecedorExternoProcessoDTO> findByCriteria(FornecedorExternoProcessoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FornecedorExternoProcesso> specification = createSpecification(criteria);
        return fornecedorExternoProcessoRepository.findAll(specification, page).map(fornecedorExternoProcessoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FornecedorExternoProcessoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FornecedorExternoProcesso> specification = createSpecification(criteria);
        return fornecedorExternoProcessoRepository.count(specification);
    }

    /**
     * Function to convert {@link FornecedorExternoProcessoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FornecedorExternoProcesso> createSpecification(FornecedorExternoProcessoCriteria criteria) {
        Specification<FornecedorExternoProcesso> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FornecedorExternoProcesso_.id));
            }
            if (criteria.getIdnVarFornecedorExterno() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getIdnVarFornecedorExterno(), FornecedorExternoProcesso_.idnVarFornecedorExterno)
                    );
            }
            if (criteria.getIdnVarProcesso() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getIdnVarProcesso(), FornecedorExternoProcesso_.idnVarProcesso));
            }
            if (criteria.getIdnvarUsuario() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getIdnvarUsuario(), FornecedorExternoProcesso_.idnvarUsuario));
            }
            if (criteria.getFornecedorExternoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getFornecedorExternoId(),
                            root -> root.join(FornecedorExternoProcesso_.fornecedorExterno, JoinType.LEFT).get(FornecedorExterno_.id)
                        )
                    );
            }
            if (criteria.getProcessoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProcessoId(),
                            root -> root.join(FornecedorExternoProcesso_.processo, JoinType.LEFT).get(Processo_.id)
                        )
                    );
            }
            if (criteria.getUsuarioId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUsuarioId(),
                            root -> root.join(FornecedorExternoProcesso_.usuario, JoinType.LEFT).get(Usuario_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
