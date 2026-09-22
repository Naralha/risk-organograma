package br.com.obelisco.risk.model;

import br.com.obelisco.risk.model.Empresa;
import br.com.obelisco.risk.model.UnidadeOrganizacional;
import br.com.obelisco.risk.model.BaseEntity;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity
@Table(name = "tb_risk_processo", schema = "risk", uniqueConstraints = @UniqueConstraint(name = "tb_risk_processo_unique", columnNames = {"empresa_id", "codigo"}))
public class Processo extends BaseEntity {
    @Column(nullable = false, length = 40) private String codigo;
    @Column(nullable = false, length = 180) private String nome;
    @Column(nullable = false, length = 1000) private String objetivo;
    @Column(length = 500) private String limiteInicial;
    @Column(length = 500) private String limiteFinal;
    @Column(length = 2000) private String entradas;
    @Column(length = 2000) private String saidas;
    @Column(length = 500) private String caminhoArquivo;
    @Column(nullable = false, length = 30) private String status = "ATIVO";
    @Column(nullable = false) private LocalDate inicio;
    private LocalDate fim;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "empresa_id", nullable = false) private Empresa empresa;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "macro_processo_id", nullable = false) private MacroProcesso macroProcesso;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unidade_id") private UnidadeOrganizacional unidade;
}
