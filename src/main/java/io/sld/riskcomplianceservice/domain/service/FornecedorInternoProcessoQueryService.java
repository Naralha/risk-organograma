package io.sld.riskcomplianceservice.domain.service;


import io.sld.riskcomplianceservice.domain.entity.*;
import io.sld.riskcomplianceservice.domain.repository.FornecedorInternoProcessoRepository;
import io.sld.riskcomplianceservice.domain.service.dto.FornecedorInternoProcessoDTO;
import io.sld.riskcomplianceservice.domain.service.criteria.FornecedorInternoProcessoCriteria;
import io.sld.riskcomplianceservice.domain.service.mapper.FornecedorInternoProcessoMapper;
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
 * Service for executing complex queries for {@link FornecedorInternoProcesso} entities in the database.
 * The main input is a {@link FornecedorInternoProcessoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FornecedorInternoProcessoDTO} or a {@link Page} of {@link FornecedorInternoProcessoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FornecedorInternoProcessoQueryService extends QueryService<FornecedorInternoProcesso> {

    private final Logger log = LoggerFactory.getLogger(FornecedorInternoProcessoQueryService.class);

    private final FornecedorInternoProcessoRepository fornecedorInternoProcessoRepository;

    private final FornecedorInternoProcessoMapper fornecedorInternoProcessoMapper;

    public FornecedorInternoProcessoQueryService(
        FornecedorInternoProcessoRepository fornecedorInternoProcessoRepository,
        FornecedorInternoProcessoMapper fornecedorInternoProcessoMapper
    ) {
        this.fornecedorInternoProcessoRepository = fornecedorInternoProcessoRepository;
        this.fornecedorInternoProcessoMapper = fornecedorInternoProcessoMapper;
    }

    /**
     * Return a {@link List} of {@link FornecedorInternoProcessoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FornecedorInternoProcessoDTO> findByCriteria(FornecedorInternoProcessoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FornecedorInternoProcesso> specification = createSpecification(criteria);
        return fornecedorInternoProcessoMapper.toDto(fornecedorInternoProcessoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FornecedorInternoProcessoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FornecedorInternoProcessoDTO> findByCriteria(FornecedorInternoProcessoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FornecedorInternoProcesso> specification = createSpecification(criteria);
        return fornecedorInternoProcessoRepository.findAll(specification, page).map(fornecedorInternoProcessoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FornecedorInternoProcessoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FornecedorInternoProcesso> specification = createSpecification(criteria);
        return fornecedorInternoProcessoRepository.count(specification);
    }

    /**
     * Function to convert {@link FornecedorInternoProcessoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FornecedorInternoProcesso> createSpecification(FornecedorInternoProcessoCriteria criteria) {
        Specification<FornecedorInternoProcesso> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FornecedorInternoProcesso_.id));
            }
            if (criteria.getIdnVarOrganograma() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getIdnVarOrganograma(), FornecedorInternoProcesso_.idnVarOrganograma)
                    );
            }
            if (criteria.getIdnVarProcesso() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getIdnVarProcesso(), FornecedorInternoProcesso_.idnVarProcesso));
            }
            if (criteria.getIdnvarUsuario() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getIdnvarUsuario(), FornecedorInternoProcesso_.idnvarUsuario));
            }
            if (criteria.getOrganogramaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrganogramaId(),
                            root -> root.join(FornecedorInternoProcesso_.organograma, JoinType.LEFT).get(Organograma_.id)
                        )
                    );
            }
            if (criteria.getProcessoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProcessoId(),
                            root -> root.join(FornecedorInternoProcesso_.processo, JoinType.LEFT).get(Processo_.id)
                        )
                    );
            }
            if (criteria.getUsuarioId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUsuarioId(),
                            root -> root.join(FornecedorInternoProcesso_.usuario, JoinType.LEFT).get(Usuario_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
