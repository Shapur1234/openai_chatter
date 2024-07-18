#!/bin/sh

./docker_build.sh

docker run -p 8080:8080 shapur1234/openaichatter
