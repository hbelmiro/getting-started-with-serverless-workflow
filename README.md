# Getting Started With Serverless Workflow

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
in the [OpenAPI document](greeting-flow/src/main/resources/international-greeting-service.yml).

---

When both services are up and running, you can try the following command:

```shell
curl -X POST -H 'Content-Type:application/json' -H 'Accept:application/json' -d '{"workflowdata" : {"name": "Helber", "country": "Brazil"}}' http://localhost:8080/greeting
```

The above command should print an output similar to the following:

```shell
{"id":"09f0c9f9-6327-47cd-b6d8-55814bfd4a27","workflowdata":{"greeting":"Saudações do Serverless Workflow, Helber!"}}
```