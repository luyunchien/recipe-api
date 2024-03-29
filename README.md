# Recipe API

Spring Boot demo project.

My attempt to learn REST APIs in Java.

## Features

- CI with [GitHub Actions](.github/workflows/java.yml)
- Server models and stubs [generated](https://openapi-generator.tech/docs/generators/spring/) from an
  [OpenAPI specification](./openapi.json).
- Persistence using [Hibernate ORM](https://hibernate.org/orm/).
- Error handling using [problem-spring-web](https://github.com/zalando/problem-spring-web) to produce consistent
  RFC 7807 responses.
- [Integration testing](server/src/test/java/com/luyunchien/recipe/api/RecipeControllerIT.java) 
  using [generated java client](java-client/pom.xml) from OpenAPI specification.