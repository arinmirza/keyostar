#!/usr/bin/env bash
set -euxo pipefail # being extra careful, see https://gist.github.com/akrasic/380bda362e0420be08709152c91ca1f9

docker build -t valonis:local .
minikube image load valonis:local --overwrite

kubectl rollout restart deployment/valonis-gateway
kubectl rollout restart statefulset/valonis-store

kubectl rollout status deployment/valonis-gateway
kubectl rollout status statefulset/valonis-store