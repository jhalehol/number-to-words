#!/bin/bash

# Build the application
mvn clean package

IMAGE_TAG="converter";

# Build the image
docker build -t $IMAGE_TAG .