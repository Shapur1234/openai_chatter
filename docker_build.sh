#!/bin/sh

./gradlew build && java -jar build/libs/openaichatter.jar

docker build --build-arg JAR_FILE=build/libs/\*.jar -t shapur1234/openaichatter .
