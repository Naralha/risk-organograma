# Risk

Módulo Risk para gestão organizacional, processos, relacionamentos e compliance. A estrutura segue o backend Obelisco para permitir futura incorporação ao Resseguro:

- namespace `br.com.obelisco.risk`;
- Spring Boot 3.3.2 e Java 21;
- packages `business`, `endpoint`, `model`, `model.dto` e `repository`;
- SQL Server no banco `ElyxDB`;
- schema isolado `risk` e tabelas `tb_risk_*`;
- Flyway 10.22.0.

## Requisitos

- Java 21
- Docker, opcional
- SQL Server para execução integrada
- Maven Wrapper incluído no repositório

## Execução local

O perfil `local` usa um banco H2 persistido em `target/h2db` e carrega os dados de desenvolvimento:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

A API fica disponível em `http://localhost:8080`.

- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI: `http://localhost:8080/v3/api-docs`
- Health: `http://localhost:8080/actuator/health`
- H2 Console, somente no perfil local: `http://localhost:8080/h2-console`

## Configuração integrada

A execução sem o perfil local usa SQL Server. Configure o ambiente:

| Variável | Descrição | Padrão |
| --- | --- | --- |
| `SPRING_DATASOURCE_URL` | URL JDBC do SQL Server | banco `ElyxDB` em `localhost:1433` |
| `SPRING_DATASOURCE_USERNAME` | Usuário do banco | `sa` |
| `SPRING_DATASOURCE_PASSWORD` | Senha do banco | vazio |
| `SERVER_PORT` | Porta HTTP | `8080` |

Secrets devem ser fornecidos pelo ambiente e não devem ser versionados.

## Build e testes

```bash
./mvnw clean verify
```

O relatório JaCoCo é gerado em `target/site/jacoco/index.html`.

## Ambiente local completo com Docker Compose

```bash
./infra/local-environment.sh
```

O script cria o `.env` local quando necessário, monta as imagens e aguarda os healthchecks. O Compose sobe SQL Server, cria o banco `ElyxDB`, executa as migrations Flyway, inicia a API e publica o frontend React em `http://localhost:5173`. O Nginx do frontend encaminha `/api` para a aplicação dentro da rede Docker.

Os serviços seguem o padrão do Obelisco: `sql-server`, `mssqltools`, `app`, `front` e `integration-tests`. A API usa Maven 3.9.4 com Temurin 21 no build, Temurin 21 no runtime e usuário sem privilégios.

```bash
./infra/local-environment.sh status
./infra/local-environment.sh logs app
./infra/local-environment.sh test
./infra/local-environment.sh down
```

Use `./infra/local-environment.sh help` para ver todos os comandos. `reset` também apaga o volume local do SQL Server antes de recriar o ambiente.

## Flyway e incorporação no Obelisco

O Obelisco já ocupa `V1` a `V10`. O contrato do módulo Risk começa em:

- `V11__risk_initial_structure.sql` — schema e estrutura;
- `V12__risk_development_data.sql` — dados apenas para desenvolvimento.

As migrations `V1`/`V2` permanecem temporariamente para validar bancos Risk existentes. Ao incorporar o módulo ao repositório Obelisco, somente `V11` e migrations posteriores do módulo devem entrar no diretório compartilhado.

O usuário inicial é `admin`, com senha `password`; altere-o antes de usar fora do ambiente local.
