# Time Stamp

![Version 1.0](https://img.shields.io/badge/version-1.0-yellow.svg)
[![MIT License](http://img.shields.io/badge/license-MIT-blue.svg?style=flat)](LICENSE)

This app is a SNS that allows you to share past stamps within your organization.

## Develop

### Requirements

- Java OpenJDK 11
- Spring Boot 2
- MySQL 8.0

### Usage

If you want to run on Windows, you can use `gradlew.bat` instead of of `gradlew`.

#### How to bulid

```sh
$ ./gradlew bootJar
```

When build successful, you can find .jar file in `app/build/libs`

#### How to run

First, you need to launch mysql with `docker-compose`.

```sh
$ docker-compose up -d
```

Then you can launch application.
Default port is `8080`. If you want to change port, run with `-Dserver.port=XXXX`.

```sh
# 1. run .jar file
$ java -jar timestamp-<version>.jar  # -Dspring.profiles.active=<environment>

# 2. run on dev environment
$ ./gradlew bootRun
```

#### How to test

```sh
# 1. all tests
$ ./gradlew test

# 2. only unit tests
$ ./gradlew unitTest

# 3. only integration tests
$ ./gradlew integrationTest
```

### API docs

This project support Swagger UI.

1. Run application
2. Access to [Swagger UI](http://localhost:8080/swagger-ui/)

## Wiki

- [miro - specification](https://miro.com/app/board/o9J_lzi-YAc=/)
- [figma - mockup](https://www.figma.com/file/aidhNiwV3e26OlLbZ5Ke5c/TimeStamp-RCC-SummerHack-2021?node-id=0%3A1)
- [wiki - tips](https://github.com/ritscc/hack-2021-1-c/wiki)
