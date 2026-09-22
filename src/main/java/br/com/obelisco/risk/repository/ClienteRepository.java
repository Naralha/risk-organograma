package br.com.obelisco.risk.repository;

import br.com.obelisco.risk.model.ClienteExterno;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<ClienteExterno, UUID> {
    @EntityGraph(attributePaths = "empresa")
    Page<ClienteExterno> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
