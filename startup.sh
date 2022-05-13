#!/bin/bash

cd getting-started-with-sw || exit
./mvnw clean package -Dquarkus.container-image.build=true

cd ../translation-service || exit
./mvnw clean package -Dquarkus.container-image.build=true

cd ../docker-compose || exit
docker-compose up