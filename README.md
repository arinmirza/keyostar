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

Keyostar exposes different API prefixes depending on the configured instance mode. Clients are expected to communicate with a gateway instance through `/gateway`. Store instances expose an analogous `/store` API, primarily for internal communication and direct testing.

| Mode | Method | Endpoint | Request Body | Success Response | Other Responses |
|---|---|---|---|---|---|
| Gateway | `GET` | `/gateway/key/{key}` | — | `200 OK` with the stored value as plain text | `400 Bad Request` for invalid keys, `404 Not Found` if the key does not exist, `503 Service Unavailable` if the responsible store cannot be reached, `500 Internal Server Error` for unexpected errors |
| Gateway | `PUT` | `/gateway/key/{key}` | Value as plain text | Forwards the store response; normally `204 No Content` | `400 Bad Request` for invalid key/value, `503 Service Unavailable` if the responsible store cannot be reached, `500 Internal Server Error` for unexpected errors |
| Gateway | `DELETE` | `/gateway/key/{key}` | — | `204 No Content` if the key existed | `400 Bad Request` for invalid keys, `404 Not Found` if the key does not exist, `503 Service Unavailable` if the responsible store cannot be reached, `500 Internal Server Error` for unexpected errors |
| Gateway | `GET` | `/gateway/stats` | — | `200 OK` with a JSON array containing the statistics reported by each configured store | Unreachable stores are represented by an empty object in the returned array |
| Store | `GET` | `/store/key/{key}` | — | `200 OK` with the stored value as plain text | `400 Bad Request` for invalid keys, `404 Not Found` if the key does not exist |
| Store | `PUT` | `/store/key/{key}` | Value as plain text | `204 No Content` | `400 Bad Request` for invalid key/value |
| Store | `DELETE` | `/store/key/{key}` | — | `204 No Content` if the key existed | `400 Bad Request` for invalid keys, `404 Not Found` if the key does not exist |
| Store | `GET` | `/store/stats` | — | `200 OK` with a JSON object of the form `{ "size": "<number>" }` | — |

Keys must be non-blank and may contain at most `MAX_KEY_LENGTH` characters. Values must not be `null` and may contain at most `MAX_VALUE_LENGTH` characters. Validation failures return `400 Bad Request` together with a plain-text description of the first detected violation.

Successful `GET` requests return values as plain text. `PUT` and `DELETE` requests do not return a response body. Statistics endpoints return JSON.

### Environment Variables

Keyostar uses Spring Boot configuration properties and can be configured through environment variables. The same application image is used for both gateway and store instances; `KEYOSTAR_INSTANCE_MODE` determines which role is activated.

| Environment Variable | Description | Typical Value / Example | Used By |
|---|---|---|---|
| `KEYOSTAR_INSTANCE_MODE` | Determines the role of the running instance. | `GATEWAY`, `STORE` | All instances |
| `SERVER_PORT` | HTTP port on which the Spring Boot instance listens. | `8080` | All instances |
| `KEYOSTAR_GATEWAY_ADDRESSING` | Selects how gateway instances resolve store addresses. | `localhost`, `docker`, `kubernetes` | Gateway |
| `KEYOSTAR_GATEWAY_HASH_FUNCTION` | Selects the hash function used for partitioning keys. | `java` | Gateway |
| `KEYOSTAR_GATEWAY_STORE_COUNT` | Number of store instances across which the key space is partitioned. This value is fixed at startup. | `3` | Gateway |
| `KEYOSTAR_LOCAL_STORE_BASE_PORT` | Base port used by the localhost address resolver. Store `i` is expected at `basePort + i`. | `9080` | Gateway in localhost mode |
| `KEYOSTAR_DOCKER_STORE_HOST_TEMPLATE` | Docker Compose hostname template used to resolve stores. | `store-%d` | Gateway in Docker mode |
| `KEYOSTAR_DOCKER_STORE_PORT` | Internal port used by store containers. | `8080` | Gateway in Docker mode |
| `KEYOSTAR_KUBERNETES_STORE_STATEFUL_SET_NAME` | Name of the Kubernetes StatefulSet used to construct stable store DNS names. | `keyostar-store` | Gateway in Kubernetes mode |
| `KEYOSTAR_KUBERNETES_STORE_SERVICE_NAME` | Name of the headless Kubernetes Service through which individual stores are resolved. | `keyostar-store` | Gateway in Kubernetes mode |
| `KEYOSTAR_KUBERNETES_STORE_PORT` | Port exposed by store pods inside the Kubernetes cluster. | `8080` | Gateway in Kubernetes mode |
| `KEYOSTAR_OBSERVABILITY_LOGGER` | Selects the configured logging implementation. | `console` | All instances |
| `KEYOSTAR_OBSERVABILITY_LOG_LEVEL` | Configures the application log level. | `trace` | All instances |


### Design Document
TBD 

## Tests
No tests are implemented due to the scope of the task. This will be discussed conceptually during the interview.

## Benchmarks
No benchmarks are implemented due to the scope of the task. This will be discussed conceptually during the interview.

## AI Disclosure

### Implementation
The implementation was written entirely by me. During the development process, I used generative AI to learn more about the technology stack, discuss and evaluate design ideas, brainstorm possible approaches, and review my code.

### README.md
This `README.md` was written by me and refined using generative AI for grammar, clarity, and conciseness. The API endpoint and environment variable tables were generated entirely with the assistance of AI. All AI-generated content was reviewed and verified by me for correctness.was reviewed and verified by me for correctness. 