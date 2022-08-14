package io.sld.riskcomplianceservice.domain.service;

import io.sld.riskcomplianceservice.domain.entity.*;
import io.sld.riskcomplianceservice.domain.repository.ClienteExternoRepository;
import io.sld.riskcomplianceservice.domain.service.dto.ClienteExternoDTO;
import io.sld.riskcomplianceservice.domain.service.criteria.ClienteExternoCriteria;
import io.sld.riskcomplianceservice.domain.service.mapper.ClienteExternoMapper;
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
 * Service for executing complex queries for {@link ClienteExterno} entities in the database.
 * The main input is a {@link ClienteExternoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ClienteExternoDTO} or a {@link Page} of {@link ClienteExternoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ClienteExternoQueryService extends QueryService<ClienteExterno> {

    private final Logger log = LoggerFactory.getLogger(ClienteExternoQueryService.class);

    private final ClienteExternoRepository clienteExternoRepository;

    private final ClienteExternoMapper clienteExternoMapper;

    public ClienteExternoQueryService(ClienteExternoRepository clienteExternoRepository, ClienteExternoMapper clienteExternoMapper) {
        this.clienteExternoRepository = clienteExternoRepository;
        this.clienteExternoMapper = clienteExternoMapper;
    }

    /**
     * Return a {@link List} of {@link ClienteExternoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ClienteExternoDTO> findByCriteria(ClienteExternoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ClienteExterno> specification = createSpecification(criteria);
        return clienteExternoMapper.toDto(clienteExternoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ClienteExternoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClienteExternoDTO> findByCriteria(ClienteExternoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ClienteExterno> specification = createSpecification(criteria);
        return clienteExternoRepository.findAll(specification, page).map(clienteExternoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ClienteExternoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ClienteExterno> specification = createSpecification(criteria);
        return clienteExternoRepository.count(specification);
    }

    /**
     * Function to convert {@link ClienteExternoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ClienteExterno> createSpecification(ClienteExternoCriteria criteria) {
        Specification<ClienteExterno> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ClienteExterno_.id));
            }
            if (criteria.getIdnVarClienteExterno() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getIdnVarClienteExterno(), ClienteExterno_.idnVarClienteExterno));
            }
            if (criteria.getnVarNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getnVarNome(), ClienteExterno_.nVarNome));
            }
            if (criteria.getnVarDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getnVarDescricao(), ClienteExterno_.nVarDescricao));
            }
            if (criteria.getIdnVarEmpresa() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdnVarEmpresa(), ClienteExterno_.idnVarEmpresa));
            }
            if (criteria.getIdnvarUsuario() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdnvarUsuario(), ClienteExterno_.idnvarUsuario));
            }
            if (criteria.getClienteExternoProcessoId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getClienteExternoProcessoId(),
                            root -> root.join(ClienteExterno_.clienteExternoProcessos, JoinType.LEFT).get(ClienteExternoProcesso_.id)
                        )
                    );
            }
            if (criteria.getEmpresaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmpresaId(),
                            root -> root.join(ClienteExterno_.empresa, JoinType.LEFT).get(Empresa_.id)
                        )
                    );
            }
            if (criteria.getUsuarioId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUsuarioId(),
                            root -> root.join(ClienteExterno_.usuario, JoinType.LEFT).get(Usuario_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
