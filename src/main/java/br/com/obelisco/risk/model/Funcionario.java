package br.com.obelisco.risk.model;

import br.com.obelisco.risk.model.Empresa;
import br.com.obelisco.risk.model.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity
@Table(name = "tb_risk_funcionario", schema = "risk", uniqueConstraints = {
    @UniqueConstraint(name = "tb_risk_funcionario_codigo_unique", columnNames = {"empresa_id", "codigo"}),
    @UniqueConstraint(name = "tb_risk_funcionario_email_unique", columnNames = {"empresa_id", "email"})
})
public class Funcionario extends BaseEntity {
    @Column(nullable = false, length = 40) private String codigo;
    @Column(nullable = false, length = 180) private String nome;
    @Column(nullable = false, length = 180) private String email;
    @Column(length = 1000) private String descricao;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "empresa_id", nullable = false) private Empresa empresa;
}
