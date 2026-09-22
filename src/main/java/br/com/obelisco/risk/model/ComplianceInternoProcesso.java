package br.com.obelisco.risk.model;

import br.com.obelisco.risk.model.Processo;
import br.com.obelisco.risk.model.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Entity
@Table(name = "tb_risk_compliance_interno_processo", schema = "risk", uniqueConstraints = @UniqueConstraint(name = "tb_risk_compliance_interno_processo_unique", columnNames = {"compliance_id", "processo_id"}))
public class ComplianceInternoProcesso extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "compliance_id", nullable = false) private ComplianceInterno compliance;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "processo_id", nullable = false) private Processo processo;
}
