#!/usr/bin/env bash
set -euo pipefail

IMAGE="keyostar:local"

echo "🔵 [INFO] Building image [$IMAGE]."
docker build -t "$IMAGE" .

echo "🔵 [INFO] Starting Keyostar with Docker Compose."
docker compose up -d

echo "🟢 [OK] Keyostar started."
echo "Run 'docker compose down' to terminate."