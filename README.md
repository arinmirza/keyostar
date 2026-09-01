# Keyostar

Keyostar is a simple and efficient distributed in-memory key-value store implemented using Java and Spring Boot.

## Deployment

Keyostar application can be deployed in three different settings: locally, using docker and using Kubernetes.
The easiest any recommended way is to use the Kubernetes approach.

### Using Kubernetes
Navigate to the repository root and execute the following command:

```sh
./build-and-run-kubernetes.sh --delete-previous --port-forward
```
- The `--delete-previous` flag will stop the previous Kubernetes deployment if it exists, and has no effect otherwise. Without this flag you can also manually delete the previously deployments.
  ```sh
  kubectl delete -f k8s/ --ignore-not-found
  ```
- The `--port-forward` flag will start the port forwarding process after the application is built and deployed successfully. Without this flag, you can also port forward manually:
  ```sh
  kubectl port-forward service/keyostar-gateway 8080:8080
  ```
The Kubernetes manifests are found under the `k8s` directory. By default, they are configured to instantiate 2 gateway and 3 store instances. The port is also configured as 8080. Please refer to the environment variables section for details.

### Using Docker
Navigate to the repository root and execute the following command:
```sh
./build-and-run-docker.sh 
```
If you wish to skip the build and deploy directly, you can use:
```sh
docker compose up
```
The project will be built and deployed as configured in `compose.yaml` file. By default, 2 gateway and 3 store instances will be instantiated. 
To stop the deployment, you can use:
```sh
docker compose down
```

### Locally
Navigate to the repository root and execute the following command:
```sh
./build-and-run-localhost.sh 
```
This will build and deploy 1 gateway and 3 store instances. The gateway will be hosted at `8080`, and the stores will be hosted at ports `9080..9082`. To change the defaults, you can either modify the script or run the commands manually yourself.

## User Interface
Keyostar provides a basic client interface implemented in Angular as a single page application (SPA).

<img width="1266" height="622" alt="ui-screenshot" src="https://github.com/user-attachments/assets/94df1921-fdc5-418b-bb86-1875d4a1b1c1" />

The implementation is not polished, and can be started in development mode as follows:
Navigate to the `ui` directory under the root, and then execute the following command:
```shell
npx ng serve --proxy-config proxy.conf.json
```
- The `--proxy-config` flag is configured to look at the `proxy.conf.json` file which rewrites the HTTP requests to `/api/` endpoint to `http://localhost:8080`. This is a pragmatic workaround for circumventing around CORS issues during local deployments.

If you are using a node version manager, make sure to activate the desired environment for that shell session. The application was developed using `nvm` version `2.2.14` and `npm` version `v25.6.1`. 
```shell
nvm use 25
```
The UI was prepared using the headless component library `zard-ui` which is a port of `shadcn-ui``to Angular ecosystem.

## Documentation

### API

### Design Document
TBD 

## Tests
No tests are implemented due to the scope of the task. This will be discussed conceptually during the interview.

## Benchmarks
No benchmarks are implemented due to the scope of the task. This will be discussed conceptually during the interview.
