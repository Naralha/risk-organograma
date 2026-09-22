package br.com.obelisco.risk.model;

import br.com.obelisco.risk.model.UnidadeOrganizacional;
import br.com.obelisco.risk.model.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity
@Table(name = "tb_risk_macro_processo_unidade", schema = "risk", uniqueConstraints = @UniqueConstraint(name = "tb_risk_macro_processo_unidade_unique", columnNames = {"macro_processo_id", "unidade_id"}))
public class MacroProcessoUnidade extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "macro_processo_id", nullable = false) private MacroProcesso macroProcesso;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "unidade_id", nullable = false) private UnidadeOrganizacional unidade;
}
