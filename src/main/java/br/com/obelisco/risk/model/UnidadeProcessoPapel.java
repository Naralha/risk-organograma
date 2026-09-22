package br.com.obelisco.risk.model;

import br.com.obelisco.risk.model.UnidadeOrganizacional;
import br.com.obelisco.risk.model.Processo;
import br.com.obelisco.risk.model.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity
@Table(name = "tb_risk_unidade_processo_papel", schema = "risk", uniqueConstraints = @UniqueConstraint(name = "tb_risk_unidade_processo_papel_unique", columnNames = {"unidade_id", "processo_id", "papel"}))
public class UnidadeProcessoPapel extends BaseEntity {
    public enum Papel { CLIENTE_INTERNO, FORNECEDOR_INTERNO }
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "unidade_id", nullable = false) private UnidadeOrganizacional unidade;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "processo_id", nullable = false) private Processo processo;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30) private Papel papel;
}
