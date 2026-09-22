CREATE TABLE empresa (
    id varchar(36) NOT NULL PRIMARY KEY, codigo varchar(40) NOT NULL, nome varchar(180) NOT NULL,
    descricao varchar(1000), created_at datetime NOT NULL, updated_at datetime NOT NULL,
    CONSTRAINT uk_empresa_codigo UNIQUE (codigo)
);

CREATE TABLE usuario (
    id varchar(36) NOT NULL PRIMARY KEY, login varchar(80) NOT NULL, nome varchar(180) NOT NULL,
    senha_hash varchar(255) NOT NULL, perfil varchar(30) NOT NULL, ativo bit NOT NULL, empresa_id varchar(36) NOT NULL,
    created_at datetime NOT NULL, updated_at datetime NOT NULL,
    CONSTRAINT uk_usuario_login UNIQUE (login), CONSTRAINT fk_usuario_empresa FOREIGN KEY (empresa_id) REFERENCES empresa(id)
);

CREATE TABLE unidade_organizacional (
    id varchar(36) NOT NULL PRIMARY KEY, codigo varchar(40) NOT NULL, nome varchar(180) NOT NULL,
    descricao varchar(1000), empresa_id varchar(36) NOT NULL, parent_id varchar(36), created_at datetime NOT NULL, updated_at datetime NOT NULL,
    CONSTRAINT uk_unidade_empresa_codigo UNIQUE (empresa_id,codigo),
    CONSTRAINT fk_unidade_empresa FOREIGN KEY (empresa_id) REFERENCES empresa(id),
    CONSTRAINT fk_unidade_parent FOREIGN KEY (parent_id) REFERENCES unidade_organizacional(id)
);

CREATE TABLE funcionario (
    id varchar(36) NOT NULL PRIMARY KEY, codigo varchar(40) NOT NULL, nome varchar(180) NOT NULL,
    email varchar(180) NOT NULL, descricao varchar(1000), empresa_id varchar(36) NOT NULL, created_at datetime NOT NULL, updated_at datetime NOT NULL,
    CONSTRAINT uk_funcionario_empresa_codigo UNIQUE (empresa_id,codigo), CONSTRAINT uk_funcionario_empresa_email UNIQUE (empresa_id,email),
    CONSTRAINT fk_funcionario_empresa FOREIGN KEY (empresa_id) REFERENCES empresa(id)
);

CREATE TABLE funcionario_unidade (
    id varchar(36) NOT NULL PRIMARY KEY, funcionario_id varchar(36) NOT NULL, unidade_id varchar(36) NOT NULL,
    inicio date NOT NULL, fim date, responsavel bit NOT NULL, created_at datetime NOT NULL, updated_at datetime NOT NULL,
    CONSTRAINT uk_funcionario_unidade UNIQUE (funcionario_id,unidade_id),
    CONSTRAINT fk_fu_funcionario FOREIGN KEY (funcionario_id) REFERENCES funcionario(id),
    CONSTRAINT fk_fu_unidade FOREIGN KEY (unidade_id) REFERENCES unidade_organizacional(id)
);

CREATE TABLE macro_processo (
    id varchar(36) NOT NULL PRIMARY KEY, codigo varchar(40) NOT NULL, nome varchar(180) NOT NULL,
    descricao varchar(1000), empresa_id varchar(36) NOT NULL, created_at datetime NOT NULL, updated_at datetime NOT NULL,
    CONSTRAINT uk_macro_empresa_codigo UNIQUE (empresa_id,codigo), CONSTRAINT fk_macro_empresa FOREIGN KEY (empresa_id) REFERENCES empresa(id)
);

CREATE TABLE macro_processo_unidade (
    id varchar(36) NOT NULL PRIMARY KEY, macro_processo_id varchar(36) NOT NULL, unidade_id varchar(36) NOT NULL,
    created_at datetime NOT NULL, updated_at datetime NOT NULL, CONSTRAINT uk_macro_unidade UNIQUE (macro_processo_id,unidade_id),
    CONSTRAINT fk_mpu_macro FOREIGN KEY (macro_processo_id) REFERENCES macro_processo(id),
    CONSTRAINT fk_mpu_unidade FOREIGN KEY (unidade_id) REFERENCES unidade_organizacional(id)
);

CREATE TABLE processo (
    id varchar(36) NOT NULL PRIMARY KEY, codigo varchar(40) NOT NULL, nome varchar(180) NOT NULL, objetivo varchar(1000) NOT NULL,
    limite_inicial varchar(500), limite_final varchar(500), entradas varchar(2000), saidas varchar(2000), caminho_arquivo varchar(500),
    status varchar(30) NOT NULL, inicio date NOT NULL, fim date, empresa_id varchar(36) NOT NULL, macro_processo_id varchar(36) NOT NULL,
    unidade_id varchar(36), created_at datetime NOT NULL, updated_at datetime NOT NULL,
    CONSTRAINT uk_processo_empresa_codigo UNIQUE (empresa_id,codigo), CONSTRAINT fk_processo_empresa FOREIGN KEY (empresa_id) REFERENCES empresa(id),
    CONSTRAINT fk_processo_macro FOREIGN KEY (macro_processo_id) REFERENCES macro_processo(id),
    CONSTRAINT fk_processo_unidade FOREIGN KEY (unidade_id) REFERENCES unidade_organizacional(id)
);

CREATE TABLE cliente_externo (
    id varchar(36) NOT NULL PRIMARY KEY, codigo varchar(40) NOT NULL, nome varchar(180) NOT NULL, descricao varchar(1000),
    empresa_id varchar(36) NOT NULL, created_at datetime NOT NULL, updated_at datetime NOT NULL,
    CONSTRAINT uk_cliente_empresa_codigo UNIQUE (empresa_id,codigo), CONSTRAINT fk_cliente_empresa FOREIGN KEY (empresa_id) REFERENCES empresa(id)
);
CREATE TABLE fornecedor_externo (
    id varchar(36) NOT NULL PRIMARY KEY, codigo varchar(40) NOT NULL, nome varchar(180) NOT NULL, descricao varchar(1000),
    empresa_id varchar(36) NOT NULL, created_at datetime NOT NULL, updated_at datetime NOT NULL,
    CONSTRAINT uk_fornecedor_empresa_codigo UNIQUE (empresa_id,codigo), CONSTRAINT fk_fornecedor_empresa FOREIGN KEY (empresa_id) REFERENCES empresa(id)
);
CREATE TABLE compliance_interno (
    id varchar(36) NOT NULL PRIMARY KEY, codigo varchar(40) NOT NULL, nome varchar(180) NOT NULL, descricao varchar(1000),
    empresa_id varchar(36) NOT NULL, created_at datetime NOT NULL, updated_at datetime NOT NULL,
    CONSTRAINT uk_ci_empresa_codigo UNIQUE (empresa_id,codigo), CONSTRAINT fk_ci_empresa FOREIGN KEY (empresa_id) REFERENCES empresa(id)
);
CREATE TABLE compliance_externo (
    id varchar(36) NOT NULL PRIMARY KEY, codigo varchar(40) NOT NULL, nome varchar(180) NOT NULL, descricao varchar(1000),
    empresa_id varchar(36) NOT NULL, created_at datetime NOT NULL, updated_at datetime NOT NULL,
    CONSTRAINT uk_ce_empresa_codigo UNIQUE (empresa_id,codigo), CONSTRAINT fk_ce_empresa FOREIGN KEY (empresa_id) REFERENCES empresa(id)
);

CREATE TABLE cliente_processo (
    id varchar(36) NOT NULL PRIMARY KEY, cliente_id varchar(36) NOT NULL, processo_id varchar(36) NOT NULL, created_at datetime NOT NULL, updated_at datetime NOT NULL,
    CONSTRAINT uk_cliente_processo UNIQUE (cliente_id,processo_id), CONSTRAINT fk_cp_cliente FOREIGN KEY (cliente_id) REFERENCES cliente_externo(id), CONSTRAINT fk_cp_processo FOREIGN KEY (processo_id) REFERENCES processo(id)
);
CREATE TABLE fornecedor_processo (
    id varchar(36) NOT NULL PRIMARY KEY, fornecedor_id varchar(36) NOT NULL, processo_id varchar(36) NOT NULL, created_at datetime NOT NULL, updated_at datetime NOT NULL,
    CONSTRAINT uk_fornecedor_processo UNIQUE (fornecedor_id,processo_id), CONSTRAINT fk_fp_fornecedor FOREIGN KEY (fornecedor_id) REFERENCES fornecedor_externo(id), CONSTRAINT fk_fp_processo FOREIGN KEY (processo_id) REFERENCES processo(id)
);
CREATE TABLE unidade_processo_papel (
    id varchar(36) NOT NULL PRIMARY KEY, unidade_id varchar(36) NOT NULL, processo_id varchar(36) NOT NULL, papel varchar(30) NOT NULL, created_at datetime NOT NULL, updated_at datetime NOT NULL,
    CONSTRAINT uk_unidade_processo_papel UNIQUE (unidade_id,processo_id,papel), CONSTRAINT fk_upp_unidade FOREIGN KEY (unidade_id) REFERENCES unidade_organizacional(id), CONSTRAINT fk_upp_processo FOREIGN KEY (processo_id) REFERENCES processo(id)
);
CREATE TABLE compliance_interno_processo (
    id varchar(36) NOT NULL PRIMARY KEY, compliance_id varchar(36) NOT NULL, processo_id varchar(36) NOT NULL, created_at datetime NOT NULL, updated_at datetime NOT NULL,
    CONSTRAINT uk_ci_processo UNIQUE (compliance_id,processo_id), CONSTRAINT fk_cip_ci FOREIGN KEY (compliance_id) REFERENCES compliance_interno(id), CONSTRAINT fk_cip_processo FOREIGN KEY (processo_id) REFERENCES processo(id)
);
CREATE TABLE compliance_externo_processo (
    id varchar(36) NOT NULL PRIMARY KEY, compliance_id varchar(36) NOT NULL, processo_id varchar(36) NOT NULL, created_at datetime NOT NULL, updated_at datetime NOT NULL,
    CONSTRAINT uk_ce_processo UNIQUE (compliance_id,processo_id), CONSTRAINT fk_cep_ce FOREIGN KEY (compliance_id) REFERENCES compliance_externo(id), CONSTRAINT fk_cep_processo FOREIGN KEY (processo_id) REFERENCES processo(id)
);

CREATE INDEX ix_unidade_empresa ON unidade_organizacional(empresa_id);
CREATE INDEX ix_processo_macro ON processo(macro_processo_id);
CREATE INDEX ix_processo_unidade ON processo(unidade_id);
