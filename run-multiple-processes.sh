#!/bin/bash

MAIN_CLASS="org.assessment.multipleprocess.Main"

echo "Compiling"
mvn clean compile

echo "Executing"
java -cp target/classes $MAIN_CLASS