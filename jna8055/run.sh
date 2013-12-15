#!/bin/bash

rm -rf dist/

echo "Run k8055 test application"
ant jar

sudo java -cp lib/jna-4.0.0.jar:dist/k8055.jar JnaK8055

