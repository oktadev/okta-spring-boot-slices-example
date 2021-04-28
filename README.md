# Spring Boot Test slices

This example service - visitors counter demonstrates fundamental use of `@DataJpaTest`, `@WebMvcTest` including authentication with OpenID Connect.

**Prerequisites:**
 
- [Java 11](https://adoptopenjdk.net/)+
- [Okta CLI 0.8.0+](https://cli.okta.com)
- [Docker & Docker-Compose (for development environment)](https://docs.docker.com/get-docker/)

> [Okta](https://developer.okta.com/) has Authentication and User Management APIs that reduce development time with instant-on, scalable user infrastructure. Okta's intuitive API and expert support make it easy for developers to authenticate, manage and secure users and roles in any application.

## Getting Started

The app is using:
* **Okta** as identity provider
* **PostgreSQL** as database engine
* **Spring JPA** for database access
* **docker-compose** for local development environment 
* **kotlin** as programming language

Start with cloning this repository:

```
git clone https://github.com/ruXlab/okta-spring-boot-slices
cd okta-spring-boot-slices
```

Start local development environment
```
docker-compose -f docker-compose.devenv.yml up
```

In another console start the app
```
./gradlew bootRun
```
Then, open **http://localhost:8080** in your favorite browser to see service working.

## Help

Please post any questions as comments on the [blog post](https://developer.okta.com/blog/2021/04/21/spring-boot-testing-slices), or visit our [Okta Developer Forums](https://devforum.okta.com/).

## License

Apache 2.0, see [LICENSE](LICENSE).
