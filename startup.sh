#!/bin/bash

cd greeting-flow || exit
./mvnw clean package -Dquarkus.container-image.build=true

cd ../international-greeting-service || exit
./mvnw clean package -Dquarkus.container-image.build=true

cd ../docker-compose || exit
docker-compose up