#!/usr/bin/env bash
set -euo pipefail # being extra careful, see https://gist.github.com/akrasic/380bda362e0420be08709152c91ca1f9

FLAG_DELETE_PREVIOUS=false
FLAG_PORT_FORWARD=false

TAG="local"
IMAGE="keyostar:$TAG"

for arg in "$@"; do
  case "$arg" in
    --delete-previous)
      FLAG_DELETE_PREVIOUS=true
      ;;
    --port-forward)
      FLAG_PORT_FORWARD=true
      ;;
    *)
      echo "🔴 [ERROR] Unknown argument: $arg"
      exit 1
      ;;
  esac
done

if [ "$FLAG_DELETE_PREVIOUS" = true ]; then
  echo "🔵 [INFO] Deleting previous Kubernetes resources."
  kubectl delete -f k8s/ --ignore-not-found
fi

echo "🔵 [INFO] Building image [$IMAGE]."
docker build -t "$IMAGE" .

echo "🔵 [INFO] Loading image [$IMAGE] to minikube."
minikube image load "$IMAGE"

echo "🔵 [INFO] Applying Kubernetes manifests."
kubectl apply -f k8s/

echo "🔵 [INFO] Waiting for rollouts."
kubectl rollout status deployment/keyostar-gateway
kubectl rollout status statefulset/keyostar-store

echo "🟢 [OK] Successfully deployed [$IMAGE]."

if [ "$FLAG_PORT_FORWARD" = true ]; then
  echo "🔵 [INFO] Port forwarding Keyostar-gateway on [localhost:8080]."
  kubectl port-forward service/keyostar-gateway 8080:8080
fi