#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$ROOT_DIR"

if [[ -f .env ]]; then
  set -a
  # shellcheck disable=SC1091
  source .env
  set +a
fi

PIDS=()

cleanup() {
  echo ""
  echo "Stopping Java/frontend processes..."
  for pid in "${PIDS[@]:-}"; do
    if kill -0 "$pid" 2>/dev/null; then
      kill "$pid" 2>/dev/null || true
    fi
  done
  wait || true
  echo "PostgreSQL container is still running (docker compose stop to stop it)."
}
trap cleanup EXIT INT TERM

wait_for_http() {
  local url="$1"
  local name="$2"
  local attempts="${3:-90}"
  for ((i = 1; i <= attempts; i++)); do
    if curl -fsS "$url" >/dev/null 2>&1; then
      echo "$name is ready: $url"
      return 0
    fi
    sleep 1
  done
  echo "Timed out waiting for $name at $url" >&2
  return 1
}

require_cmd() {
  if ! command -v "$1" >/dev/null 2>&1; then
    echo "Missing required command: $1" >&2
    exit 1
  fi
}

require_cmd docker
require_cmd curl
require_cmd java
require_cmd mvn
require_cmd npm

echo "Starting PostgreSQL..."
docker compose up -d

echo "Waiting for PostgreSQL to become healthy..."
for ((i = 1; i <= 60; i++)); do
  status="$(docker inspect --format='{{if .State.Health}}{{.State.Health.Status}}{{else}}{{.State.Status}}{{end}}' devplatform-postgres 2>/dev/null || true)"
  if [[ "$status" == "healthy" ]]; then
    echo "PostgreSQL is healthy."
    break
  fi
  if ((i == 60)); then
    echo "Timed out waiting for PostgreSQL healthcheck (last status: ${status:-unknown})." >&2
    exit 1
  fi
  sleep 1
done

if [[ ! -f gestion-projets-service/target/gestion-projets-service-1.0-SNAPSHOT.jar ]]; then
  echo "Building gestion-projets-service..."
  (cd gestion-projets-service && mvn -DskipTests package)
fi

if [[ ! -f api-gateway/target/api-gateway-1.0-SNAPSHOT.jar ]]; then
  echo "Building api-gateway..."
  (cd api-gateway && mvn -DskipTests package)
fi

if [[ ! -d interface/node_modules ]]; then
  echo "Installing interface dependencies..."
  (cd interface && npm install)
fi

SOAP_PORT="${SOAP_PORT:-8081}"
SOAP_PATH="${SOAP_PATH:-/ws/projets}"
export GATEWAY_PORT="${GATEWAY_PORT:-${PORT:-9000}}"

echo "Starting gestion-projets-service (SOAP)..."
(
  cd gestion-projets-service
  exec java -jar target/gestion-projets-service-1.0-SNAPSHOT.jar
) &
PIDS+=("$!")
wait_for_http "http://localhost:${SOAP_PORT}${SOAP_PATH}?wsdl" "gestion-projets-service"

echo "Starting api-gateway (REST)..."
(
  cd api-gateway
  exec java -jar target/api-gateway-1.0-SNAPSHOT.jar
) &
PIDS+=("$!")
wait_for_http "http://localhost:${GATEWAY_PORT}/api/projects" "api-gateway"

echo "Starting interface (Vite)..."
(
  cd interface
  exec npm run dev -- --host 0.0.0.0 --port 5173
) &
PIDS+=("$!")

echo ""
echo "All services started."
echo "  SOAP WSDL:  http://localhost:${SOAP_PORT}${SOAP_PATH}?wsdl"
echo "  REST API:   http://localhost:${GATEWAY_PORT}/api/projects"
echo "  Interface:  http://localhost:5173"
echo ""
echo "Press Ctrl+C to stop Java/frontend processes."

wait -n "${PIDS[@]}"