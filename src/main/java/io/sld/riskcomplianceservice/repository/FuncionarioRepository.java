package io.sld.riskcomplianceservice.repository;

import io.sld.riskcomplianceservice.domain.Funcionario;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Funcionario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>, JpaSpecificationExecutor<Funcionario> {}
