package br.com.obelisco.risk.model;

import br.com.obelisco.risk.model.Empresa;
import br.com.obelisco.risk.model.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity
@Table(name = "tb_risk_usuario", schema = "risk", uniqueConstraints = @UniqueConstraint(name = "tb_risk_usuario_unique", columnNames = "login"))
public class Usuario extends BaseEntity {
    @Column(nullable = false, length = 80) private String login;
    @Column(nullable = false, length = 180) private String nome;
    @Column(nullable = false, length = 255) private String senhaHash;
    @Column(nullable = false, length = 30) private String perfil = "USER";
    @Column(nullable = false) private boolean ativo = true;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "empresa_id", nullable = false) private Empresa empresa;
}
