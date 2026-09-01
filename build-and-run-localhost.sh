#!/usr/bin/env bash
set -euo pipefail

STORE_COUNT=3
GATEWAY_PORT=8080
STORE_BASE_PORT=9080

echo "🔵 [INFO] Building Keyostar."
./mvnw clean package -DskipTests

JAR=$(find target -maxdepth 1 -name 'keyostar-*.jar' | head -n 1)

PIDS=()

echo "🔵 [INFO] Starting $STORE_COUNT store instances."

for i in $(seq 0 $((STORE_COUNT - 1))); do
  PORT=$((STORE_BASE_PORT + i))

  echo "🔵 [INFO] Starting store $i on port $PORT."

  KEYOSTAR_INSTANCE_MODE=STORE \
  SERVER_PORT="$PORT" \
  java -jar "$JAR" \
    > "store-$i.log" 2>&1 &

  PID=$!
  PIDS+=("$PID")

  echo "   Store $i PID: $PID"
done

echo "🔵 [INFO] Starting gateway on port $GATEWAY_PORT."

KEYOSTAR_INSTANCE_MODE=GATEWAY \
KEYOSTAR_GATEWAY_ADDRESSING=localhost \
KEYOSTAR_GATEWAY_HASH_FUNCTION=java \
KEYOSTAR_GATEWAY_STORE_COUNT="$STORE_COUNT" \
KEYOSTAR_LOCAL_STORE_BASE_PORT="$STORE_BASE_PORT" \
SERVER_PORT="$GATEWAY_PORT" \
java -jar "$JAR" \
  > gateway.log 2>&1 &

GATEWAY_PID=$!
PIDS+=("$GATEWAY_PID")

echo "   Gateway PID: $GATEWAY_PID"

echo
echo "🟢 [OK] Keyostar started locally."
echo "Gateway: http://localhost:$GATEWAY_PORT"
echo
echo "Process IDs:"
printf '%s ' "${PIDS[@]}"
echo "Use 'kill ...' to terminate the processes."