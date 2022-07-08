package io.sld.riskcomplianceservice.service;

import io.sld.riskcomplianceservice.domain.*; // for static metamodels
import io.sld.riskcomplianceservice.domain.MacroProcessoOrganograma;
import io.sld.riskcomplianceservice.repository.MacroProcessoOrganogramaRepository;
import io.sld.riskcomplianceservice.service.criteria.MacroProcessoOrganogramaCriteria;
import io.sld.riskcomplianceservice.service.dto.MacroProcessoOrganogramaDTO;
import io.sld.riskcomplianceservice.service.mapper.MacroProcessoOrganogramaMapper;
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
 * Service for executing complex queries for {@link MacroProcessoOrganograma} entities in the database.
 * The main input is a {@link MacroProcessoOrganogramaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MacroProcessoOrganogramaDTO} or a {@link Page} of {@link MacroProcessoOrganogramaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MacroProcessoOrganogramaQueryService extends QueryService<MacroProcessoOrganograma> {

    private final Logger log = LoggerFactory.getLogger(MacroProcessoOrganogramaQueryService.class);

    private final MacroProcessoOrganogramaRepository macroProcessoOrganogramaRepository;

    private final MacroProcessoOrganogramaMapper macroProcessoOrganogramaMapper;

    public MacroProcessoOrganogramaQueryService(
        MacroProcessoOrganogramaRepository macroProcessoOrganogramaRepository,
        MacroProcessoOrganogramaMapper macroProcessoOrganogramaMapper
    ) {
        this.macroProcessoOrganogramaRepository = macroProcessoOrganogramaRepository;
        this.macroProcessoOrganogramaMapper = macroProcessoOrganogramaMapper;
    }

    /**
     * Return a {@link List} of {@link MacroProcessoOrganogramaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MacroProcessoOrganogramaDTO> findByCriteria(MacroProcessoOrganogramaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MacroProcessoOrganograma> specification = createSpecification(criteria);
        return macroProcessoOrganogramaMapper.toDto(macroProcessoOrganogramaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MacroProcessoOrganogramaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MacroProcessoOrganogramaDTO> findByCriteria(MacroProcessoOrganogramaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MacroProcessoOrganograma> specification = createSpecification(criteria);
        return macroProcessoOrganogramaRepository.findAll(specification, page).map(macroProcessoOrganogramaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MacroProcessoOrganogramaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MacroProcessoOrganograma> specification = createSpecification(criteria);
        return macroProcessoOrganogramaRepository.count(specification);
    }

    /**
     * Function to convert {@link MacroProcessoOrganogramaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MacroProcessoOrganograma> createSpecification(MacroProcessoOrganogramaCriteria criteria) {
        Specification<MacroProcessoOrganograma> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MacroProcessoOrganograma_.id));
            }
            if (criteria.getIdnVarMacroProcesso() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getIdnVarMacroProcesso(), MacroProcessoOrganograma_.idnVarMacroProcesso)
                    );
            }
            if (criteria.getIdnVarOrganograma() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getIdnVarOrganograma(), MacroProcessoOrganograma_.idnVarOrganograma)
                    );
            }
            if (criteria.getIdnVarProcesso() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getIdnVarProcesso(), MacroProcessoOrganograma_.idnVarProcesso));
            }
            if (criteria.getIdnVarUsuario() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getIdnVarUsuario(), MacroProcessoOrganograma_.idnVarUsuario));
            }
            if (criteria.getIdnVarEmpresa() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getIdnVarEmpresa(), MacroProcessoOrganograma_.idnVarEmpresa));
            }
            if (criteria.getIdnVarUsuarioCadastro() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getIdnVarUsuarioCadastro(), MacroProcessoOrganograma_.idnVarUsuarioCadastro)
                    );
            }
            if (criteria.getEmpresaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmpresaId(),
                            root -> root.join(MacroProcessoOrganograma_.empresa, JoinType.LEFT).get(Empresa_.id)
                        )
                    );
            }
            if (criteria.getUsuarioId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUsuarioId(),
                            root -> root.join(MacroProcessoOrganograma_.usuario, JoinType.LEFT).get(Usuario_.id)
                        )
                    );
            }
            if (criteria.getMacroProcessoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMacroProcessoId(),
                            root -> root.join(MacroProcessoOrganograma_.macroProcesso, JoinType.LEFT).get(MacroProcesso_.id)
                        )
                    );
            }
            if (criteria.getOrganogramaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrganogramaId(),
                            root -> root.join(MacroProcessoOrganograma_.organograma, JoinType.LEFT).get(Organograma_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
