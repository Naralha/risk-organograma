package br.com.obelisco.risk.model;

import br.com.obelisco.risk.model.Processo;
import br.com.obelisco.risk.model.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity
@Table(name = "tb_risk_fornecedor_processo", schema = "risk", uniqueConstraints = @UniqueConstraint(name = "tb_risk_fornecedor_processo_unique", columnNames = {"fornecedor_id", "processo_id"}))
public class FornecedorProcesso extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fornecedor_id", nullable = false) private FornecedorExterno fornecedor;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "processo_id", nullable = false) private Processo processo;
}
