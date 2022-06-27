# urlShortener

## Task Description
The goal of the task is to create an url shortener service with implemented statistics.

## Run project

To run the project locally first you need to have a postgreSQL database running in a docker container.

You can start a docker container with the given docker-compose file from project root:
```Shell
cd src/main/resources/migration/docker
docker-compose up
```

Run project using maven:

```Shell
mvn spring-boot:run
```

Or you can run it by using IDE

## Usage examples

You can try out api using [swagger](http://localhost:8080/swagger-ui/) or with given examples

### Generate short url

```Shell
curl -d '{"original": "https://www.kinopoisk.ru/"}' -H "Content-Type: application/json" -X POST http://localhost:8080/generatemvn spring-boot:run
```

### Redirect

Redirect by link ``localhost:8080/l/d9110f4a-f2f4-410e-be40-3801b506188e`` where "d9110f4a-f2f4-410e-be40-3801b506188e"
is your shortened url 

### Get statistics of singular url

```Shell
curl -X GET http://localhost:8080/stats/d9110f4a-f2f4-410e-be40-3801b506188e | jq
```

### Get paged statistics

```Shell
curl -X GET http://localhost:8080/stats?page=1&count=2
```

## Backlog
- Add CI/CD and Heroku support
- Containerize with Docker
- Improve the singular statistics method (I don't like that we have to fetch all entities from the database)
- Add more tests (unit-tests and integration tests)
