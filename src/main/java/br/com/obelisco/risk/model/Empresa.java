package br.com.obelisco.risk.model;

import br.com.obelisco.risk.model.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity
@Table(name = "tb_risk_empresa", schema = "risk", uniqueConstraints = @UniqueConstraint(name = "tb_risk_empresa_unique", columnNames = "codigo"))
public class Empresa extends BaseEntity {
    @Column(nullable = false, length = 40) private String codigo;
    @Column(nullable = false, length = 180) private String nome;
    @Column(length = 1000) private String descricao;
}
