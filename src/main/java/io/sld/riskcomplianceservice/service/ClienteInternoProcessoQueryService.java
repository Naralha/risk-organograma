package io.sld.riskcomplianceservice.service;

import io.sld.riskcomplianceservice.domain.*; // for static metamodels
import io.sld.riskcomplianceservice.domain.ClienteInternoProcesso;
import io.sld.riskcomplianceservice.repository.ClienteInternoProcessoRepository;
import io.sld.riskcomplianceservice.service.criteria.ClienteInternoProcessoCriteria;
import io.sld.riskcomplianceservice.service.dto.ClienteInternoProcessoDTO;
import io.sld.riskcomplianceservice.service.mapper.ClienteInternoProcessoMapper;
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
 * Service for executing complex queries for {@link ClienteInternoProcesso} entities in the database.
 * The main input is a {@link ClienteInternoProcessoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ClienteInternoProcessoDTO} or a {@link Page} of {@link ClienteInternoProcessoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ClienteInternoProcessoQueryService extends QueryService<ClienteInternoProcesso> {

    private final Logger log = LoggerFactory.getLogger(ClienteInternoProcessoQueryService.class);

    private final ClienteInternoProcessoRepository clienteInternoProcessoRepository;

    private final ClienteInternoProcessoMapper clienteInternoProcessoMapper;

    public ClienteInternoProcessoQueryService(
        ClienteInternoProcessoRepository clienteInternoProcessoRepository,
        ClienteInternoProcessoMapper clienteInternoProcessoMapper
    ) {
        this.clienteInternoProcessoRepository = clienteInternoProcessoRepository;
        this.clienteInternoProcessoMapper = clienteInternoProcessoMapper;
    }

    /**
     * Return a {@link List} of {@link ClienteInternoProcessoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ClienteInternoProcessoDTO> findByCriteria(ClienteInternoProcessoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ClienteInternoProcesso> specification = createSpecification(criteria);
        return clienteInternoProcessoMapper.toDto(clienteInternoProcessoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ClienteInternoProcessoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClienteInternoProcessoDTO> findByCriteria(ClienteInternoProcessoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ClienteInternoProcesso> specification = createSpecification(criteria);
        return clienteInternoProcessoRepository.findAll(specification, page).map(clienteInternoProcessoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ClienteInternoProcessoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ClienteInternoProcesso> specification = createSpecification(criteria);
        return clienteInternoProcessoRepository.count(specification);
    }

    /**
     * Function to convert {@link ClienteInternoProcessoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ClienteInternoProcesso> createSpecification(ClienteInternoProcessoCriteria criteria) {
        Specification<ClienteInternoProcesso> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ClienteInternoProcesso_.id));
            }
            if (criteria.getIdnVarProcesso() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getIdnVarProcesso(), ClienteInternoProcesso_.idnVarProcesso));
            }
            if (criteria.getIdnVarOrganograma() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getIdnVarOrganograma(), ClienteInternoProcesso_.idnVarOrganograma));
            }
            if (criteria.getIdnvarUsuario() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getIdnvarUsuario(), ClienteInternoProcesso_.idnvarUsuario));
            }
            if (criteria.getOrganogramaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrganogramaId(),
                            root -> root.join(ClienteInternoProcesso_.organograma, JoinType.LEFT).get(Organograma_.id)
                        )
                    );
            }
            if (criteria.getProcessoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProcessoId(),
                            root -> root.join(ClienteInternoProcesso_.processo, JoinType.LEFT).get(Processo_.id)
                        )
                    );
            }
            if (criteria.getUsuarioId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUsuarioId(),
                            root -> root.join(ClienteInternoProcesso_.usuario, JoinType.LEFT).get(Usuario_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
