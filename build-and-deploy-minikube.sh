#!/usr/bin/env bash
set -euo pipefail # being extra careful, see https://gist.github.com/akrasic/380bda362e0420be08709152c91ca1f9

TAG="local"
IMAGE="keyostar:$TAG"

echo "🔵 [INFO] Building image [$IMAGE]."
docker build -t "$IMAGE" .

echo "🔵 [INFO] Loading image [$IMAGE] to minikube."
minikube image load "$IMAGE"

echo "🔵 [INFO] Applying Kubernetes manifests."
kubectl apply -f k8s/

#echo "🔵 [INFO] Updating workload images."
#kubectl set image deployment/keyostar-gateway keyostar="$IMAGE"
#kubectl set image statefulset/keyostar-store keyostar="$IMAGE"

echo "🔵 [INFO] Waiting for rollouts."
kubectl rollout status deployment/keyostar-gateway
kubectl rollout status statefulset/keyostar-store

echo "🟢 [OK] Successfully deployed [$IMAGE]."