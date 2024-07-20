#!/bin/bash

echo "Set DB Connection"

export TITANIC_QUEST_DATABASE_USERNAME=admin
export TITANIC_QUEST_DATABASE_PASSWORD=admin
export TITANIC_QUEST_DATABASE_HOST=localhost
export TITANIC_QUEST_DATABASE_PORT=5432
export TITANIC_QUEST_DATABASE_DBNAME=titanic_quest

echo "Run"

mvn spring-boot:run