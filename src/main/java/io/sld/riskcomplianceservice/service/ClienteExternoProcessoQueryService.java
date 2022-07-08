package io.sld.riskcomplianceservice.service;

import io.sld.riskcomplianceservice.domain.*; // for static metamodels
import io.sld.riskcomplianceservice.domain.ClienteExternoProcesso;
import io.sld.riskcomplianceservice.repository.ClienteExternoProcessoRepository;
import io.sld.riskcomplianceservice.service.criteria.ClienteExternoProcessoCriteria;
import io.sld.riskcomplianceservice.service.dto.ClienteExternoProcessoDTO;
import io.sld.riskcomplianceservice.service.mapper.ClienteExternoProcessoMapper;
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
 * Service for executing complex queries for {@link ClienteExternoProcesso} entities in the database.
 * The main input is a {@link ClienteExternoProcessoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ClienteExternoProcessoDTO} or a {@link Page} of {@link ClienteExternoProcessoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ClienteExternoProcessoQueryService extends QueryService<ClienteExternoProcesso> {

    private final Logger log = LoggerFactory.getLogger(ClienteExternoProcessoQueryService.class);

    private final ClienteExternoProcessoRepository clienteExternoProcessoRepository;

    private final ClienteExternoProcessoMapper clienteExternoProcessoMapper;

    public ClienteExternoProcessoQueryService(
        ClienteExternoProcessoRepository clienteExternoProcessoRepository,
        ClienteExternoProcessoMapper clienteExternoProcessoMapper
    ) {
        this.clienteExternoProcessoRepository = clienteExternoProcessoRepository;
        this.clienteExternoProcessoMapper = clienteExternoProcessoMapper;
    }

    /**
     * Return a {@link List} of {@link ClienteExternoProcessoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ClienteExternoProcessoDTO> findByCriteria(ClienteExternoProcessoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ClienteExternoProcesso> specification = createSpecification(criteria);
        return clienteExternoProcessoMapper.toDto(clienteExternoProcessoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ClienteExternoProcessoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClienteExternoProcessoDTO> findByCriteria(ClienteExternoProcessoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ClienteExternoProcesso> specification = createSpecification(criteria);
        return clienteExternoProcessoRepository.findAll(specification, page).map(clienteExternoProcessoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ClienteExternoProcessoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ClienteExternoProcesso> specification = createSpecification(criteria);
        return clienteExternoProcessoRepository.count(specification);
    }

    /**
     * Function to convert {@link ClienteExternoProcessoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ClienteExternoProcesso> createSpecification(ClienteExternoProcessoCriteria criteria) {
        Specification<ClienteExternoProcesso> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ClienteExternoProcesso_.id));
            }
            if (criteria.getIdnVarClienteExterno() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getIdnVarClienteExterno(), ClienteExternoProcesso_.idnVarClienteExterno)
                    );
            }
            if (criteria.getIdnVarProcesso() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getIdnVarProcesso(), ClienteExternoProcesso_.idnVarProcesso));
            }
            if (criteria.getIdnvarUsuario() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getIdnvarUsuario(), ClienteExternoProcesso_.idnvarUsuario));
            }
            if (criteria.getClienteExternoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getClienteExternoId(),
                            root -> root.join(ClienteExternoProcesso_.clienteExterno, JoinType.LEFT).get(ClienteExterno_.id)
                        )
                    );
            }
            if (criteria.getProcessoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProcessoId(),
                            root -> root.join(ClienteExternoProcesso_.processo, JoinType.LEFT).get(Processo_.id)
                        )
                    );
            }
            if (criteria.getUsuarioId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUsuarioId(),
                            root -> root.join(ClienteExternoProcesso_.usuario, JoinType.LEFT).get(Usuario_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
