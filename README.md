# TitanicQuest

Проект написан на
- Java 17
- Angular 18.1.1
- DataBase - PostgreSQL
- Cache - Redis

## Development server

Для запуска сервера в дефолтной конфигурации понадобится настроить переменные среды

### Параметры базы данных
 - TITANIC_QUEST_DATABASE_USERNAME - имя пользователя
 - TITANIC_QUEST_DATABASE_PASSWORD - пароль
 - TITANIC_QUEST_DATABASE_HOST - хост
 - TITANIC_QUEST_DATABASE_PORT - порт
 - TITANIC_QUEST_DATABASE_DBNAME - база данных

### Параметры Redis
Если вы не хотите использовать кешироание с помошью Redis `TITANIC_QUEST_REDIS_ENABLE=false`
 - TITANIC_QUEST_REDIS_ENABLE - использовать кеширование или нет
 - TITANIC_QUEST_REDIS_HOST - хост
 - TITANIC_QUEST_REDIS_PORT - порт
 - TITANIC_QUEST_REDIS_PASS - пасс

### Опционально
Вы можете назначить порт сервера `TITANIC_QUEST_SERVER_PORT=ваш_порт`

Также вы можете внести свои значения в скрипт `run.sh` и запустить сервер выполнив его или
после настройки выше указанных переменных, можете запустить сервер командой *Maven* `mvn spring-boot:run`

## Development Redis

Вы можете запустить экземпляр Redis с помощью *Docker* `docker run -p 16379:6379 -d redis:6.0 redis-server --requirepass "mypass"`

## Development client

Репозиторий с клиентом - https://github.com/Davidchanz/TitanicQuestWebClient

Вы можете запустить клиент в Live режиме с помощью команды `ng serve`. Клиент вы найдёте по адресу `http://localhost:4200/`.
Или вы можете сделать деплой на нужный вам web-server для этого

 - Соберите клиента с помощью команды `ng build`
 - Настройте конфигурацию 
   - `TITANIC_QUEST_SERVER_HOST=хост_сервера`
   - `TITANIC_QUEST_SERVER_PORT=порт_сервера`
 - Выполните команду `envsubst < ./dist/titanic-quest-web-client/browser/assets/config/config.dev.js > ./dist/titanic-quest-web-client/browser/assets/config/config.js`
