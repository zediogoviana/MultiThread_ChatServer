#!/bin/bash

set -e

if [ $1 == "client" ] ; then
  rm -rf chat-client/target/
  cp config.properties chat-client/src/main/resources/
  cd chat-client/
  mvn clean package
  mycommand="java -jar target/chat-client-1.0-SNAPSHOT-jar-with-dependencies.jar"
elif [ $1 == "server" ]; then
  rm -rf chat-server/target/
  cd chat-server/
  mvn clean package
  mycommand="java -jar target/chat-server-1.0-SNAPSHOT-jar-with-dependencies.jar"
else
  echo "$1 is not a readable entry"
  exit 2
fi

echo $mycommand
$mycommand
