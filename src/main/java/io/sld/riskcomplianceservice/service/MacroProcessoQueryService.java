package io.sld.riskcomplianceservice.service;

import io.sld.riskcomplianceservice.domain.*; // for static metamodels
import io.sld.riskcomplianceservice.domain.MacroProcesso;
import io.sld.riskcomplianceservice.repository.MacroProcessoRepository;
import io.sld.riskcomplianceservice.service.criteria.MacroProcessoCriteria;
import io.sld.riskcomplianceservice.service.dto.MacroProcessoDTO;
import io.sld.riskcomplianceservice.service.mapper.MacroProcessoMapper;
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
 * Service for executing complex queries for {@link MacroProcesso} entities in the database.
 * The main input is a {@link MacroProcessoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MacroProcessoDTO} or a {@link Page} of {@link MacroProcessoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MacroProcessoQueryService extends QueryService<MacroProcesso> {

    private final Logger log = LoggerFactory.getLogger(MacroProcessoQueryService.class);

    private final MacroProcessoRepository macroProcessoRepository;

    private final MacroProcessoMapper macroProcessoMapper;

    public MacroProcessoQueryService(MacroProcessoRepository macroProcessoRepository, MacroProcessoMapper macroProcessoMapper) {
        this.macroProcessoRepository = macroProcessoRepository;
        this.macroProcessoMapper = macroProcessoMapper;
    }

    /**
     * Return a {@link List} of {@link MacroProcessoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MacroProcessoDTO> findByCriteria(MacroProcessoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MacroProcesso> specification = createSpecification(criteria);
        return macroProcessoMapper.toDto(macroProcessoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MacroProcessoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MacroProcessoDTO> findByCriteria(MacroProcessoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MacroProcesso> specification = createSpecification(criteria);
        return macroProcessoRepository.findAll(specification, page).map(macroProcessoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MacroProcessoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MacroProcesso> specification = createSpecification(criteria);
        return macroProcessoRepository.count(specification);
    }

    /**
     * Function to convert {@link MacroProcessoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MacroProcesso> createSpecification(MacroProcessoCriteria criteria) {
        Specification<MacroProcesso> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MacroProcesso_.id));
            }
            if (criteria.getIdnVarMacroProcesso() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getIdnVarMacroProcesso(), MacroProcesso_.idnVarMacroProcesso));
            }
            if (criteria.getnVarNomeMacroProcesso() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getnVarNomeMacroProcesso(), MacroProcesso_.nVarNomeMacroProcesso));
            }
            if (criteria.getIdnVarEmpresa() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdnVarEmpresa(), MacroProcesso_.idnVarEmpresa));
            }
            if (criteria.getIdnVarUsuario() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdnVarUsuario(), MacroProcesso_.idnVarUsuario));
            }
            if (criteria.getMacroProcessoOrganogramaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMacroProcessoOrganogramaId(),
                            root -> root.join(MacroProcesso_.macroProcessoOrganogramas, JoinType.LEFT).get(MacroProcessoOrganograma_.id)
                        )
                    );
            }
            if (criteria.getEmpresaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmpresaId(),
                            root -> root.join(MacroProcesso_.empresa, JoinType.LEFT).get(Empresa_.id)
                        )
                    );
            }
            if (criteria.getUsuarioId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUsuarioId(),
                            root -> root.join(MacroProcesso_.usuario, JoinType.LEFT).get(Usuario_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
