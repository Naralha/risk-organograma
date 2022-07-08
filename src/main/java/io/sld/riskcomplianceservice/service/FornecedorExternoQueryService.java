package io.sld.riskcomplianceservice.service;

import io.sld.riskcomplianceservice.domain.*; // for static metamodels
import io.sld.riskcomplianceservice.domain.FornecedorExterno;
import io.sld.riskcomplianceservice.repository.FornecedorExternoRepository;
import io.sld.riskcomplianceservice.service.criteria.FornecedorExternoCriteria;
import io.sld.riskcomplianceservice.service.dto.FornecedorExternoDTO;
import io.sld.riskcomplianceservice.service.mapper.FornecedorExternoMapper;
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
 * Service for executing complex queries for {@link FornecedorExterno} entities in the database.
 * The main input is a {@link FornecedorExternoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FornecedorExternoDTO} or a {@link Page} of {@link FornecedorExternoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FornecedorExternoQueryService extends QueryService<FornecedorExterno> {

    private final Logger log = LoggerFactory.getLogger(FornecedorExternoQueryService.class);

    private final FornecedorExternoRepository fornecedorExternoRepository;

    private final FornecedorExternoMapper fornecedorExternoMapper;

    public FornecedorExternoQueryService(
        FornecedorExternoRepository fornecedorExternoRepository,
        FornecedorExternoMapper fornecedorExternoMapper
    ) {
        this.fornecedorExternoRepository = fornecedorExternoRepository;
        this.fornecedorExternoMapper = fornecedorExternoMapper;
    }

    /**
     * Return a {@link List} of {@link FornecedorExternoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FornecedorExternoDTO> findByCriteria(FornecedorExternoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FornecedorExterno> specification = createSpecification(criteria);
        return fornecedorExternoMapper.toDto(fornecedorExternoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FornecedorExternoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FornecedorExternoDTO> findByCriteria(FornecedorExternoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FornecedorExterno> specification = createSpecification(criteria);
        return fornecedorExternoRepository.findAll(specification, page).map(fornecedorExternoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FornecedorExternoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FornecedorExterno> specification = createSpecification(criteria);
        return fornecedorExternoRepository.count(specification);
    }

    /**
     * Function to convert {@link FornecedorExternoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<FornecedorExterno> createSpecification(FornecedorExternoCriteria criteria) {
        Specification<FornecedorExterno> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), FornecedorExterno_.id));
            }
            if (criteria.getIdnVarFornecedorExterno() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getIdnVarFornecedorExterno(), FornecedorExterno_.idnVarFornecedorExterno)
                    );
            }
            if (criteria.getnVarNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getnVarNome(), FornecedorExterno_.nVarNome));
            }
            if (criteria.getnVarDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getnVarDescricao(), FornecedorExterno_.nVarDescricao));
            }
            if (criteria.getIdnVarEmpresa() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdnVarEmpresa(), FornecedorExterno_.idnVarEmpresa));
            }
            if (criteria.getIdnvarUsuario() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdnvarUsuario(), FornecedorExterno_.idnvarUsuario));
            }
            if (criteria.getFornecedorExternoProcessoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getFornecedorExternoProcessoId(),
                            root ->
                                root.join(FornecedorExterno_.fornecedorExternoProcessos, JoinType.LEFT).get(FornecedorExternoProcesso_.id)
                        )
                    );
            }
            if (criteria.getEmpresaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmpresaId(),
                            root -> root.join(FornecedorExterno_.empresa, JoinType.LEFT).get(Empresa_.id)
                        )
                    );
            }
            if (criteria.getUsuarioId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUsuarioId(),
                            root -> root.join(FornecedorExterno_.usuario, JoinType.LEFT).get(Usuario_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
