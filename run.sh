#!/bin/bash



if [ $# -eq 0 ]
then 
    mvn clean compiler:compile exec:java -Dexec.mainClass="me.web.files.Main"
else
    mvn clean compiler:compile exec:java -Dexec.mainClass="me.web.files.test.${1}"
fi
