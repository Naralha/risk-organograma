package io.sld.riskcomplianceservice.service;

import io.sld.riskcomplianceservice.domain.*; // for static metamodels
import io.sld.riskcomplianceservice.domain.Processo;
import io.sld.riskcomplianceservice.repository.ProcessoRepository;
import io.sld.riskcomplianceservice.service.criteria.ProcessoCriteria;
import io.sld.riskcomplianceservice.service.dto.ProcessoDTO;
import io.sld.riskcomplianceservice.service.mapper.ProcessoMapper;
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
 * Service for executing complex queries for {@link Processo} entities in the database.
 * The main input is a {@link ProcessoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProcessoDTO} or a {@link Page} of {@link ProcessoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProcessoQueryService extends QueryService<Processo> {

    private final Logger log = LoggerFactory.getLogger(ProcessoQueryService.class);

    private final ProcessoRepository processoRepository;

    private final ProcessoMapper processoMapper;

    public ProcessoQueryService(ProcessoRepository processoRepository, ProcessoMapper processoMapper) {
        this.processoRepository = processoRepository;
        this.processoMapper = processoMapper;
    }

    /**
     * Return a {@link List} of {@link ProcessoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProcessoDTO> findByCriteria(ProcessoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Processo> specification = createSpecification(criteria);
        return processoMapper.toDto(processoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProcessoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProcessoDTO> findByCriteria(ProcessoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Processo> specification = createSpecification(criteria);
        return processoRepository.findAll(specification, page).map(processoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProcessoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Processo> specification = createSpecification(criteria);
        return processoRepository.count(specification);
    }

    /**
     * Function to convert {@link ProcessoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Processo> createSpecification(ProcessoCriteria criteria) {
        Specification<Processo> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Processo_.id));
            }
            if (criteria.getIdnVarProcesso() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdnVarProcesso(), Processo_.idnVarProcesso));
            }
            if (criteria.getIdVarMacroProcesso() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdVarMacroProcesso(), Processo_.idVarMacroProcesso));
            }
            if (criteria.getnVarNomeProcesso() != null) {
                specification = specification.and(buildStringSpecification(criteria.getnVarNomeProcesso(), Processo_.nVarNomeProcesso));
            }
            if (criteria.getnVarObjetivoProcesso() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getnVarObjetivoProcesso(), Processo_.nVarObjetivoProcesso));
            }
            if (criteria.getnVarLimitrofeInicial() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getnVarLimitrofeInicial(), Processo_.nVarLimitrofeInicial));
            }
            if (criteria.getnVarLimitrofeFinal() != null) {
                specification = specification.and(buildStringSpecification(criteria.getnVarLimitrofeFinal(), Processo_.nVarLimitrofeFinal));
            }
            if (criteria.getnVarPathFile() != null) {
                specification = specification.and(buildStringSpecification(criteria.getnVarPathFile(), Processo_.nVarPathFile));
            }
            if (criteria.getnVarEntradas() != null) {
                specification = specification.and(buildStringSpecification(criteria.getnVarEntradas(), Processo_.nVarEntradas));
            }
            if (criteria.getnVarSaidas() != null) {
                specification = specification.and(buildStringSpecification(criteria.getnVarSaidas(), Processo_.nVarSaidas));
            }
            if (criteria.getDtInicio() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDtInicio(), Processo_.dtInicio));
            }
            if (criteria.getDtFim() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDtFim(), Processo_.dtFim));
            }
            if (criteria.getIdnVarEmpresa() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdnVarEmpresa(), Processo_.idnVarEmpresa));
            }
            if (criteria.getIdnvarUsuario() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdnvarUsuario(), Processo_.idnvarUsuario));
            }
            if (criteria.getClienteExternoProcessoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getClienteExternoProcessoId(),
                            root -> root.join(Processo_.clienteExternoProcessos, JoinType.LEFT).get(ClienteExternoProcesso_.id)
                        )
                    );
            }
            if (criteria.getComplianceExternoProcessoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getComplianceExternoProcessoId(),
                            root -> root.join(Processo_.complianceExternoProcessos, JoinType.LEFT).get(ComplianceExternoProcesso_.id)
                        )
                    );
            }
            if (criteria.getFornecedorExternoProcessoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getFornecedorExternoProcessoId(),
                            root -> root.join(Processo_.fornecedorExternoProcessos, JoinType.LEFT).get(FornecedorExternoProcesso_.id)
                        )
                    );
            }
            if (criteria.getClienteInternoProcessoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getClienteInternoProcessoId(),
                            root -> root.join(Processo_.clienteInternoProcessos, JoinType.LEFT).get(ClienteInternoProcesso_.id)
                        )
                    );
            }
            if (criteria.getComplianceInternoProcessoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getComplianceInternoProcessoId(),
                            root -> root.join(Processo_.complianceInternoProcessos, JoinType.LEFT).get(ComplianceInternoProcesso_.id)
                        )
                    );
            }
            if (criteria.getFornecedorInternoProcessoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getFornecedorInternoProcessoId(),
                            root -> root.join(Processo_.fornecedorInternoProcessos, JoinType.LEFT).get(FornecedorInternoProcesso_.id)
                        )
                    );
            }
            if (criteria.getEmpresaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getEmpresaId(), root -> root.join(Processo_.empresa, JoinType.LEFT).get(Empresa_.id))
                    );
            }
            if (criteria.getUsuarioId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getUsuarioId(), root -> root.join(Processo_.usuario, JoinType.LEFT).get(Usuario_.id))
                    );
            }
        }
        return specification;
    }
}
