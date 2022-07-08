package io.sld.riskcomplianceservice.service;

import io.sld.riskcomplianceservice.domain.*; // for static metamodels
import io.sld.riskcomplianceservice.domain.FuncionarioOrganograma;
import io.sld.riskcomplianceservice.repository.FuncionarioOrganogramaRepository;
import io.sld.riskcomplianceservice.service.criteria.FuncionarioOrganogramaCriteria;
import io.sld.riskcomplianceservice.service.dto.FuncionarioOrganogramaDTO;
import io.sld.riskcomplianceservice.service.mapper.FuncionarioOrganogramaMapper;
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
 * Service for executing complex queries for {@link FuncionarioOrganograma} entities in the database.
 * The main input is a {@link FuncionarioOrganogramaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FuncionarioOrganogramaDTO} or a {@link Page} of {@link FuncionarioOrganogramaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FuncionarioOrganogramaQueryService extends QueryService<FuncionarioOrganograma> {

    private final Logger log = LoggerFactory.getLogger(FuncionarioOrganogramaQueryService.class);

    private final FuncionarioOrganogramaRepository funcionarioOrganogramaRepository;

    private final FuncionarioOrganogramaMapper funcionarioOrganogramaMapper;

    public FuncionarioOrganogramaQueryService(
        FuncionarioOrganogramaRepository funcionarioOrganogramaRepository,
        FuncionarioOrganogramaMapper funcionarioOrganogramaMapper
    ) {
        this.funcionarioOrganogramaRepository = funcionarioOrganogramaRepository;
        this.funcionarioOrganogramaMapper = funcionarioOrganogramaMapper;
    }

    /**
     * Return a {@link List} of {@link FuncionarioOrganogramaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FuncionarioOrganogramaDTO> findByCriteria(FuncionarioOrganogramaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FuncionarioOrganograma> specification = createSpecification(criteria);
        return funcionarioOrganogramaMapper.toDto(funcionarioOrganogramaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FuncionarioOrganogramaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FuncionarioOrganogramaDTO> findByCriteria(FuncionarioOrganogramaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FuncionarioOrganograma> specification = createSpecification(criteria);
        return funcionarioOrganogramaRepository.findAll(specification, page).map(funcionarioOrganogramaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FuncionarioOrganogramaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FuncionarioOrganograma> specification = createSpecification(criteria);
        return funcionarioOrganogramaRepository.count(specification);
    }

    /**
     * Function to convert {@link FuncionarioOrganogramaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FuncionarioOrganograma> createSpecification(FuncionarioOrganogramaCriteria criteria) {
        Specification<FuncionarioOrganograma> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FuncionarioOrganograma_.id));
            }
            if (criteria.getIdnVarFuncionario() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getIdnVarFuncionario(), FuncionarioOrganograma_.idnVarFuncionario));
            }
            if (criteria.getIdnVarOrganograma() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getIdnVarOrganograma(), FuncionarioOrganograma_.idnVarOrganograma));
            }
            if (criteria.getIdnvarUsuario() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getIdnvarUsuario(), FuncionarioOrganograma_.idnvarUsuario));
            }
            if (criteria.getFuncionarioId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getFuncionarioId(),
                            root -> root.join(FuncionarioOrganograma_.funcionario, JoinType.LEFT).get(Funcionario_.id)
                        )
                    );
            }
            if (criteria.getOrganogramaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrganogramaId(),
                            root -> root.join(FuncionarioOrganograma_.organograma, JoinType.LEFT).get(Organograma_.id)
                        )
                    );
            }
            if (criteria.getUsuarioId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUsuarioId(),
                            root -> root.join(FuncionarioOrganograma_.usuario, JoinType.LEFT).get(Usuario_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
