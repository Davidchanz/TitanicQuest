#!/bin/bash

echo "Set DB Connection"

export TITANIC_QUEST_DATABASE_USERNAME=admin
export TITANIC_QUEST_DATABASE_PASSWORD=admin
export TITANIC_QUEST_DATABASE_HOST=localhost
export TITANIC_QUEST_DATABASE_PORT=5432
export TITANIC_QUEST_DATABASE_DBNAME=titanic_quest

echo "Set Redis Connection"

export TITANIC_QUEST_REDIS_ENABLE=true
export TITANIC_QUEST_REDIS_HOST=localhost
export TITANIC_QUEST_REDIS_PORT=16379
export TITANIC_QUEST_REDIS_PASS=mypass

echo "Run"

mvn spring-boot:run