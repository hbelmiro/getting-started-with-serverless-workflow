# Getting Started With Service Calls And Serverless Workflow

Read the [post related to this repository on the KIE Blog](https://blog.kie.org/2022/05/getting-started-with-service-calls-and-serverless-workflow.html).

## Deploying to Knative

Run the following command from the root of each service to deploy them to Knative:

```shell
mvn clean package -Dquarkus.kubernetes.deploy=true
```

---
**NOTE**

The container images need to be available for Knative.

---

----

This demo is composed of two services:
- The [workflow service (greeting-flow)](greeting-flow)
- The [remote service (international-greeting-service)](international-greeting-service)

You can see both services in action by running the following command:

```shell
./startup.sh
```

The above command will build the Docker images and run the [docker-compose](docker-compose/docker-compose.yml) file
located in the `docker-compose` directory.

---
**NOTE**

To run the [startup script](startup.sh) and the [docker-compose file](docker-compose/docker-compose.yml), you need to 
have Docker installed on your machine.

---

Alternatively, you can run the following commands to run the services locally:

```shell
cd greeting-flow
./mvnw quarkus:dev
```

The above command will start the workflow service at port 8080.

```shell
cd international-greeting-service
./mvnw quarkus:dev
```

The above command will start the international-greeting-service at port 8081.

---
**NOTE**

Running the services locally requires you to update the international-greeting-service URL
in the [OpenAPI document](greeting-flow/src/main/resources/openapi.yml).

---

When both services are up and running, you can try the following command:

```shell
curl -X POST -H 'Content-Type:application/json' -H 'Accept:application/json' -d '{"workflowdata" : {"name": "Helber", "country": "Brazil"}}' http://localhost:8080/greeting
```

The above command should print an output similar to the following:

```shell
{"id":"09f0c9f9-6327-47cd-b6d8-55814bfd4a27","workflowdata":{"greeting":"Saudações do Serverless Workflow, Helber!"}}
```

## Deploying to Knative

First, you need to deploy the `international-greeting-service` in order to know the URL you need to configure in the `greeting-flow` project.

### Deploying the `international-greeting-service` to Knative

Switch to the `international-greeting-service` directory:

```shell
cd international-greeting-service
```

If you're running Knative on minikube, configure the container CLI to use the container engine inside minikube:

```shell
eval $(minikube -p knative docker-env)
```

Deploy to Knative:

```shell
mvn clean package -Dquarkus.kubernetes.deploy=true
```

You should see an output similar to:

```shell
[INFO] [io.quarkus.container.image.jib.deployment.JibProcessor] Created container image dev.local/hbelmiro/international-greeting-service:1.0 (sha256:caca1a1a065013c034c66e36ea3f35064debf62f6c1ec3ef3b8234c1f3d20713)

[INFO] [io.quarkus.kubernetes.deployment.KubernetesDeployer] Deploying to knative server: https://192.168.49.2:8443/ in namespace: default.
[INFO] [io.quarkus.kubernetes.deployment.KubernetesDeployer] Applied: Service international-greeting-service.
[INFO] [io.quarkus.deployment.QuarkusAugmentor] Quarkus augmentation completed in 5330ms
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  9.424 s
[INFO] Finished at: 2022-11-26T10:30:39-03:00
[INFO] ------------------------------------------------------------------------
```

Run the following command to see the deployed service on Knative:

```
kn service list
```

You should see an output similar to:

```shell
NAME                             URL                                                                     LATEST                                 AGE   CONDITIONS   READY   REASON
international-greeting-service   http://international-greeting-service.default.10.109.169.193.sslip.io   international-greeting-service-00001   21m   3 OK / 3     True    
```

Use the service URL to configure the remote service in the `greeting-flow` project.

### Configuring the `greeting-flow` to use the deployed service

Update the `quarkus.rest-client."openapi_yml".uri` property in the `greeting-flow/src/main/resources/application.properties` file with the URL of the `international-greeting-service` Knative service.

Example:
```properties
quarkus.rest-client."openapi_yml".uri=http://international-greeting-service.default.10.109.169.193.sslip.io:80
```

### Deploying the `greeting-flow` to Knative

Follow the same steps you did to deploy the `international-greeting-service` project in the `greeting-flow` directory.

---
**NOTE**

Don't forget to configure the container CLI to use the container engine inside minikube:

```shell
eval $(minikube -p knative docker-env)
```
---

## Deploying native images to Knative

Follow the above same steps, but include `-Pnative -Dquarkus.native.native-image-xmx=4096m -Dquarkus.native.remote-container-build=true` in the Maven commands. Ex.:

```shell
mvn clean package -Pnative -Dquarkus.native.native-image-xmx=4096m -Dquarkus.native.remote-container-build=true -Dquarkus.kubernetes.deploy=true
```
