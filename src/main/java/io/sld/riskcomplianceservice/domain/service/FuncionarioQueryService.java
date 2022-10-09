package io.sld.riskcomplianceservice.domain.service;


import io.sld.riskcomplianceservice.domain.entity.*;
import io.sld.riskcomplianceservice.domain.repository.FuncionarioRepository;
import io.sld.riskcomplianceservice.domain.service.dto.FuncionarioDTO;
import io.sld.riskcomplianceservice.domain.service.criteria.FuncionarioCriteria;
import io.sld.riskcomplianceservice.domain.service.mapper.FuncionarioMapper;
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
 * Service for executing complex queries for {@link Funcionario} entities in the database.
 * The main input is a {@link FuncionarioCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FuncionarioDTO} or a {@link Page} of {@link FuncionarioDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FuncionarioQueryService extends QueryService<Funcionario> {

    private final Logger log = LoggerFactory.getLogger(FuncionarioQueryService.class);

    private final FuncionarioRepository funcionarioRepository;

    private final FuncionarioMapper funcionarioMapper;

    public FuncionarioQueryService(FuncionarioRepository funcionarioRepository, FuncionarioMapper funcionarioMapper) {
        this.funcionarioRepository = funcionarioRepository;
        this.funcionarioMapper = funcionarioMapper;
    }

    /**
     * Return a {@link List} of {@link FuncionarioDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FuncionarioDTO> findByCriteria(FuncionarioCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Funcionario> specification = createSpecification(criteria);
        return funcionarioMapper.toDto(funcionarioRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FuncionarioDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FuncionarioDTO> findByCriteria(FuncionarioCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Funcionario> specification = createSpecification(criteria);
        return funcionarioRepository.findAll(specification, page).map(funcionarioMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FuncionarioCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Funcionario> specification = createSpecification(criteria);
        return funcionarioRepository.count(specification);
    }

    /**
     * Function to convert {@link FuncionarioCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Funcionario> createSpecification(FuncionarioCriteria criteria) {
        Specification<Funcionario> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Funcionario_.id));
            }
            if (criteria.getIdnVarFuncionario() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getIdnVarFuncionario(), Funcionario_.idnVarFuncionario));
            }
            if (criteria.getnVarNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getnVarNome(), Funcionario_.nVarNome));
            }
            if (criteria.getnVarEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getnVarEmail(), Funcionario_.nVarEmail));
            }
            if (criteria.getnVarDescricao() != null) {
                specification = specification.and(buildStringSpecification(criteria.getnVarDescricao(), Funcionario_.nVarDescricao));
            }
//            if (criteria.getIdnVarEmpresa() != null) {
//                specification = specification.and(buildStringSpecification(criteria.getIdnVarEmpresa(), Funcionario_.idnVarEmpresa));
//            }
            if (criteria.getIdnvarUsuario() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdnvarUsuario(), Funcionario_.idnvarUsuario));
            }
            if (criteria.getFuncionarioOrganogramaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getFuncionarioOrganogramaId(),
                            root -> root.join(Funcionario_.funcionarioOrganogramas, JoinType.LEFT).get(FuncionarioOrganograma_.id)
                        )
                    );
            }
            if (criteria.getEmpresaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getEmpresaId(), root -> root.join(Funcionario_.empresa, JoinType.LEFT).get(Empresa_.id))
                    );
            }
            if (criteria.getUsuarioId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getUsuarioId(), root -> root.join(Funcionario_.usuario, JoinType.LEFT).get(Usuario_.id))
                    );
            }
        }
        return specification;
    }
}
