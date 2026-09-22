-- Módulo Risk: versões V1-V10 pertencem ao Obelisco.
${risk_schema_initialization};

CREATE TABLE risk.tb_risk_empresa (
    id varchar(36) NOT NULL PRIMARY KEY, codigo varchar(40) NOT NULL, nome varchar(180) NOT NULL,
    descricao varchar(1000), created_at datetime NOT NULL, updated_at datetime NOT NULL,
    CONSTRAINT tb_risk_empresa_codigo_unique UNIQUE (codigo)
);

CREATE TABLE risk.tb_risk_usuario (
    id varchar(36) NOT NULL PRIMARY KEY, login varchar(80) NOT NULL, nome varchar(180) NOT NULL,
    senha_hash varchar(255) NOT NULL, perfil varchar(30) NOT NULL, ativo bit NOT NULL, empresa_id varchar(36) NOT NULL,
    created_at datetime NOT NULL, updated_at datetime NOT NULL,
    CONSTRAINT tb_risk_usuario_login_unique UNIQUE (login), CONSTRAINT tb_risk_usuario_empresa_fk FOREIGN KEY (empresa_id) REFERENCES risk.tb_risk_empresa(id)
);

CREATE TABLE risk.tb_risk_unidade_organizacional (
    id varchar(36) NOT NULL PRIMARY KEY, codigo varchar(40) NOT NULL, nome varchar(180) NOT NULL,
    descricao varchar(1000), empresa_id varchar(36) NOT NULL, parent_id varchar(36), created_at datetime NOT NULL, updated_at datetime NOT NULL,
    CONSTRAINT tb_risk_unidade_empresa_codigo_unique UNIQUE (empresa_id,codigo),
    CONSTRAINT tb_risk_unidade_empresa_fk FOREIGN KEY (empresa_id) REFERENCES risk.tb_risk_empresa(id),
    CONSTRAINT tb_risk_unidade_parent_fk FOREIGN KEY (parent_id) REFERENCES risk.tb_risk_unidade_organizacional(id)
);

CREATE TABLE risk.tb_risk_funcionario (
    id varchar(36) NOT NULL PRIMARY KEY, codigo varchar(40) NOT NULL, nome varchar(180) NOT NULL,
    email varchar(180) NOT NULL, descricao varchar(1000), empresa_id varchar(36) NOT NULL, created_at datetime NOT NULL, updated_at datetime NOT NULL,
    CONSTRAINT tb_risk_funcionario_empresa_codigo_unique UNIQUE (empresa_id,codigo), CONSTRAINT tb_risk_funcionario_empresa_email_unique UNIQUE (empresa_id,email),
    CONSTRAINT tb_risk_funcionario_empresa_fk FOREIGN KEY (empresa_id) REFERENCES risk.tb_risk_empresa(id)
);

CREATE TABLE risk.tb_risk_funcionario_unidade (
    id varchar(36) NOT NULL PRIMARY KEY, funcionario_id varchar(36) NOT NULL, unidade_id varchar(36) NOT NULL,
    inicio date NOT NULL, fim date, responsavel bit NOT NULL, created_at datetime NOT NULL, updated_at datetime NOT NULL,
    CONSTRAINT tb_risk_funcionario_unidade_unique UNIQUE (funcionario_id,unidade_id),
    CONSTRAINT tb_risk_fu_funcionario_fk FOREIGN KEY (funcionario_id) REFERENCES risk.tb_risk_funcionario(id),
    CONSTRAINT tb_risk_fu_unidade_fk FOREIGN KEY (unidade_id) REFERENCES risk.tb_risk_unidade_organizacional(id)
);

CREATE TABLE risk.tb_risk_macro_processo (
    id varchar(36) NOT NULL PRIMARY KEY, codigo varchar(40) NOT NULL, nome varchar(180) NOT NULL,
    descricao varchar(1000), empresa_id varchar(36) NOT NULL, created_at datetime NOT NULL, updated_at datetime NOT NULL,
    CONSTRAINT tb_risk_macro_empresa_codigo_unique UNIQUE (empresa_id,codigo), CONSTRAINT tb_risk_macro_empresa_fk FOREIGN KEY (empresa_id) REFERENCES risk.tb_risk_empresa(id)
);

CREATE TABLE risk.tb_risk_macro_processo_unidade (
    id varchar(36) NOT NULL PRIMARY KEY, macro_processo_id varchar(36) NOT NULL, unidade_id varchar(36) NOT NULL,
    created_at datetime NOT NULL, updated_at datetime NOT NULL, CONSTRAINT tb_risk_macro_unidade_unique UNIQUE (macro_processo_id,unidade_id),
    CONSTRAINT tb_risk_mpu_macro_fk FOREIGN KEY (macro_processo_id) REFERENCES risk.tb_risk_macro_processo(id),
    CONSTRAINT tb_risk_mpu_unidade_fk FOREIGN KEY (unidade_id) REFERENCES risk.tb_risk_unidade_organizacional(id)
);

CREATE TABLE risk.tb_risk_processo (
    id varchar(36) NOT NULL PRIMARY KEY, codigo varchar(40) NOT NULL, nome varchar(180) NOT NULL, objetivo varchar(1000) NOT NULL,
    limite_inicial varchar(500), limite_final varchar(500), entradas varchar(2000), saidas varchar(2000), caminho_arquivo varchar(500),
    status varchar(30) NOT NULL, inicio date NOT NULL, fim date, empresa_id varchar(36) NOT NULL, macro_processo_id varchar(36) NOT NULL,
    unidade_id varchar(36), created_at datetime NOT NULL, updated_at datetime NOT NULL,
    CONSTRAINT tb_risk_processo_empresa_codigo_unique UNIQUE (empresa_id,codigo), CONSTRAINT tb_risk_processo_empresa_fk FOREIGN KEY (empresa_id) REFERENCES risk.tb_risk_empresa(id),
    CONSTRAINT tb_risk_processo_macro_fk FOREIGN KEY (macro_processo_id) REFERENCES risk.tb_risk_macro_processo(id),
    CONSTRAINT tb_risk_processo_unidade_fk FOREIGN KEY (unidade_id) REFERENCES risk.tb_risk_unidade_organizacional(id)
);

CREATE TABLE risk.tb_risk_cliente_externo (
    id varchar(36) NOT NULL PRIMARY KEY, codigo varchar(40) NOT NULL, nome varchar(180) NOT NULL, descricao varchar(1000),
    empresa_id varchar(36) NOT NULL, created_at datetime NOT NULL, updated_at datetime NOT NULL,
    CONSTRAINT tb_risk_cliente_empresa_codigo_unique UNIQUE (empresa_id,codigo), CONSTRAINT tb_risk_cliente_empresa_fk FOREIGN KEY (empresa_id) REFERENCES risk.tb_risk_empresa(id)
);
CREATE TABLE risk.tb_risk_fornecedor_externo (
    id varchar(36) NOT NULL PRIMARY KEY, codigo varchar(40) NOT NULL, nome varchar(180) NOT NULL, descricao varchar(1000),
    empresa_id varchar(36) NOT NULL, created_at datetime NOT NULL, updated_at datetime NOT NULL,
    CONSTRAINT tb_risk_fornecedor_empresa_codigo_unique UNIQUE (empresa_id,codigo), CONSTRAINT tb_risk_fornecedor_empresa_fk FOREIGN KEY (empresa_id) REFERENCES risk.tb_risk_empresa(id)
);
CREATE TABLE risk.tb_risk_compliance_interno (
    id varchar(36) NOT NULL PRIMARY KEY, codigo varchar(40) NOT NULL, nome varchar(180) NOT NULL, descricao varchar(1000),
    empresa_id varchar(36) NOT NULL, created_at datetime NOT NULL, updated_at datetime NOT NULL,
    CONSTRAINT tb_risk_ci_empresa_codigo_unique UNIQUE (empresa_id,codigo), CONSTRAINT tb_risk_ci_empresa_fk FOREIGN KEY (empresa_id) REFERENCES risk.tb_risk_empresa(id)
);
CREATE TABLE risk.tb_risk_compliance_externo (
    id varchar(36) NOT NULL PRIMARY KEY, codigo varchar(40) NOT NULL, nome varchar(180) NOT NULL, descricao varchar(1000),
    empresa_id varchar(36) NOT NULL, created_at datetime NOT NULL, updated_at datetime NOT NULL,
    CONSTRAINT tb_risk_ce_empresa_codigo_unique UNIQUE (empresa_id,codigo), CONSTRAINT tb_risk_ce_empresa_fk FOREIGN KEY (empresa_id) REFERENCES risk.tb_risk_empresa(id)
);

CREATE TABLE risk.tb_risk_cliente_processo (
    id varchar(36) NOT NULL PRIMARY KEY, cliente_id varchar(36) NOT NULL, processo_id varchar(36) NOT NULL, created_at datetime NOT NULL, updated_at datetime NOT NULL,
    CONSTRAINT tb_risk_cliente_processo_unique UNIQUE (cliente_id,processo_id), CONSTRAINT tb_risk_cp_cliente_fk FOREIGN KEY (cliente_id) REFERENCES risk.tb_risk_cliente_externo(id), CONSTRAINT tb_risk_cp_processo_fk FOREIGN KEY (processo_id) REFERENCES risk.tb_risk_processo(id)
);
CREATE TABLE risk.tb_risk_fornecedor_processo (
    id varchar(36) NOT NULL PRIMARY KEY, fornecedor_id varchar(36) NOT NULL, processo_id varchar(36) NOT NULL, created_at datetime NOT NULL, updated_at datetime NOT NULL,
    CONSTRAINT tb_risk_fornecedor_processo_unique UNIQUE (fornecedor_id,processo_id), CONSTRAINT tb_risk_fp_fornecedor_fk FOREIGN KEY (fornecedor_id) REFERENCES risk.tb_risk_fornecedor_externo(id), CONSTRAINT tb_risk_fp_processo_fk FOREIGN KEY (processo_id) REFERENCES risk.tb_risk_processo(id)
);
CREATE TABLE risk.tb_risk_unidade_processo_papel (
    id varchar(36) NOT NULL PRIMARY KEY, unidade_id varchar(36) NOT NULL, processo_id varchar(36) NOT NULL, papel varchar(30) NOT NULL, created_at datetime NOT NULL, updated_at datetime NOT NULL,
    CONSTRAINT tb_risk_unidade_processo_papel_unique UNIQUE (unidade_id,processo_id,papel), CONSTRAINT tb_risk_upp_unidade_fk FOREIGN KEY (unidade_id) REFERENCES risk.tb_risk_unidade_organizacional(id), CONSTRAINT tb_risk_upp_processo_fk FOREIGN KEY (processo_id) REFERENCES risk.tb_risk_processo(id)
);
CREATE TABLE risk.tb_risk_compliance_interno_processo (
    id varchar(36) NOT NULL PRIMARY KEY, compliance_id varchar(36) NOT NULL, processo_id varchar(36) NOT NULL, created_at datetime NOT NULL, updated_at datetime NOT NULL,
    CONSTRAINT tb_risk_ci_processo_unique UNIQUE (compliance_id,processo_id), CONSTRAINT tb_risk_cip_ci_fk FOREIGN KEY (compliance_id) REFERENCES risk.tb_risk_compliance_interno(id), CONSTRAINT tb_risk_cip_processo_fk FOREIGN KEY (processo_id) REFERENCES risk.tb_risk_processo(id)
);
CREATE TABLE risk.tb_risk_compliance_externo_processo (
    id varchar(36) NOT NULL PRIMARY KEY, compliance_id varchar(36) NOT NULL, processo_id varchar(36) NOT NULL, created_at datetime NOT NULL, updated_at datetime NOT NULL,
    CONSTRAINT tb_risk_ce_processo_unique UNIQUE (compliance_id,processo_id), CONSTRAINT tb_risk_cep_ce_fk FOREIGN KEY (compliance_id) REFERENCES risk.tb_risk_compliance_externo(id), CONSTRAINT tb_risk_cep_processo_fk FOREIGN KEY (processo_id) REFERENCES risk.tb_risk_processo(id)
);

CREATE INDEX tb_risk_unidade_empresa_idx ON risk.tb_risk_unidade_organizacional(empresa_id);
CREATE INDEX tb_risk_processo_macro_idx ON risk.tb_risk_processo(macro_processo_id);
CREATE INDEX tb_risk_processo_unidade_idx ON risk.tb_risk_processo(unidade_id);
