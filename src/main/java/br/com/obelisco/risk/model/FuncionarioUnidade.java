package br.com.obelisco.risk.model;

import br.com.obelisco.risk.model.Funcionario;
import br.com.obelisco.risk.model.BaseEntity;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity
@Table(name = "tb_risk_funcionario_unidade", schema = "risk", uniqueConstraints = @UniqueConstraint(name = "tb_risk_funcionario_unidade_unique", columnNames = {"funcionario_id", "unidade_id"}))
public class FuncionarioUnidade extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "funcionario_id", nullable = false) private Funcionario funcionario;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "unidade_id", nullable = false) private UnidadeOrganizacional unidade;
    @Column(nullable = false) private LocalDate inicio = LocalDate.now();
    private LocalDate fim;
    @Column(nullable = false) private boolean responsavel;
}
