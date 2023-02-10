package io.sld.riskcomplianceservice.domain.repository;

import io.sld.riskcomplianceservice.domain.entity.Empresa;
import io.sld.riskcomplianceservice.domain.entity.Funcionario;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data SQL repository for the Funcionario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>, JpaSpecificationExecutor<Funcionario> {
    @Query("SELECT f FROM Funcionario f WHERE f.idnVarFuncionario = ?1")
    Optional<Funcionario> findByIdnVarFuncionario(UUID idnVarFuncionario);

    void deleteByIdnVarFuncionario(UUID idnVarFuncionario);

    Optional<List<Funcionario>> findByEmpresa(Empresa empresa);
}
