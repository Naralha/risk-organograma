package br.com.obelisco.risk.repository;

import br.com.obelisco.risk.model.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnidadeRepository extends JpaRepository<UnidadeOrganizacional, UUID> {
    @EntityGraph(attributePaths = {"empresa", "parent"}) List<UnidadeOrganizacional> findByEmpresaIdOrderByNome(UUID empresaId);
}

