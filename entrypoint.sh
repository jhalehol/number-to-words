#!/bin/bash

echo "Running converter application"
java -jar /app/converter.jar && tail -f /dev/null