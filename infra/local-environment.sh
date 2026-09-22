#!/usr/bin/env bash
set -Eeuo pipefail

BACKEND_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
FRONTEND_ROOT="${RISK_FRONT_DIR:-$(cd "${BACKEND_ROOT}/../risk-front" && pwd)}"
COMPOSE_FILE="${BACKEND_ROOT}/compose.yaml"
ENV_FILE="${BACKEND_ROOT}/.env"
STARTUP_TIMEOUT="${STARTUP_TIMEOUT:-300}"

usage() {
  cat <<'EOF'
Uso: ./infra/local-environment.sh [comando] [serviço]

Comandos:
  up                 Monta as imagens e sobe banco, backend e frontend (padrão)
  down               Para e remove os containers
  restart            Reinicia todo o ambiente
  logs [serviço]     Acompanha os logs; aceita front, app ou sql-server
  status             Mostra o estado dos serviços
  test               Executa os testes de integração no ambiente Docker
  reset              Remove containers e volumes e cria o ambiente novamente
  help               Mostra esta ajuda

Variáveis opcionais:
  DATABASE_SA_PASSWORD  Senha local do SQL Server
  FRONTEND_PORT         Porta do frontend (padrão: 5173)
  STARTUP_TIMEOUT       Tempo máximo de inicialização em segundos (padrão: 300)
  RISK_FRONT_DIR        Caminho absoluto do repositório risk-front
EOF
}

fail() {
  echo "Erro: $*" >&2
  exit 1
}

require_command() {
  command -v "$1" >/dev/null 2>&1 || fail "comando '$1' não encontrado."
}

prepare() {
  require_command docker
  docker compose version >/dev/null 2>&1 || fail "Docker Compose v2 não está disponível."
  docker info >/dev/null 2>&1 || fail "Docker Engine não está em execução."
  [[ -f "${FRONTEND_ROOT}/package.json" ]] || fail "risk-front não encontrado em '${FRONTEND_ROOT}'. Configure RISK_FRONT_DIR."

  if [[ ! -f "$ENV_FILE" ]]; then
    cp "${BACKEND_ROOT}/.env.example" "$ENV_FILE"
    echo ">> Configuração local criada em ${ENV_FILE}"
  fi

  export RISK_FRONT_DIR="$FRONTEND_ROOT"
}

compose() {
  docker compose --project-directory "$BACKEND_ROOT" --env-file "$ENV_FILE" -f "$COMPOSE_FILE" "$@"
}

show_urls() {
  local frontend_port="${FRONTEND_PORT:-5173}"
  echo
  echo "Ambiente Risk disponível:"
  echo "  Frontend:   http://localhost:${frontend_port}"
  echo "  API:        http://localhost:8080/api"
  echo "  Swagger:    http://localhost:8080/swagger-ui/index.html"
  echo "  Health:     http://localhost:8080/actuator/health"
  echo "  SQL Server: localhost:1433 (ElyxDB)"
  echo "  Login:      admin / password"
  echo
  echo "Logs: ${BASH_SOURCE[0]} logs"
  echo "Parar: ${BASH_SOURCE[0]} down"
}

up() {
  echo ">> Construindo e iniciando SQL Server, backend e frontend..."
  compose up --build --detach --wait --wait-timeout "$STARTUP_TIMEOUT" sql-server mssqltools app front
  show_urls
}

command="${1:-up}"
case "$command" in
  up)
    prepare
    up
    ;;
  down)
    prepare
    compose down --remove-orphans
    ;;
  restart)
    prepare
    compose down --remove-orphans
    up
    ;;
  logs)
    prepare
    shift || true
    compose logs --follow --tail=150 "$@"
    ;;
  status)
    prepare
    compose ps
    ;;
  test)
    prepare
    compose --profile tests run --rm integration-tests
    ;;
  reset)
    prepare
    echo ">> Removendo containers e dados locais do Risk..."
    compose down --volumes --remove-orphans
    up
    ;;
  help|-h|--help)
    usage
    ;;
  *)
    usage >&2
    fail "comando desconhecido: $command"
    ;;
esac
