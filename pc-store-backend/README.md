# PC Store (backend part)
## Environments
### Local
Will be started with H2 as database.

`--spring.profiles.active=local`

maven command: `mvn clean package -Plocal`

Swagger ui interface for testing: http://localhost:8080/swagger-ui/index.html
### Development
Will be started with Postgres (as a Docker-image) as database and no security.

`--spring.profiles.active=dev`

maven command: `mvn clean package -Pdev`

#### Docker
- Configure docker image:
  - `docker run --name postgresql-dev -p 5432:5432 -e POSTGRES_PASSWORD=pcstorepassword -d postgres`


- Docker console commands for prepare postgre database:
  - `bash` - Start bash console.
  - `su postgres` - Switch user to postgres.
  - `psql` - start postgres console.
  - `CREATE USER pcstoreadmin WITH PASSWORD '6tfgv6zghb6';` - create a database user.
  - `CREATE DATABASE pcstore;` - create a database schema.

### Production
Will be started with Postgres as database and security (TLS and authorization).

`--spring.profiles.active=prod`

maven command: `mvn clean package -Pprod`

## Security

### User management

Keyclock

#### Generate a TLS-certificate:

`/c/Program\ Files/Java/jdk-16.0.1/bin/keytool.exe -genkeypair -alias smart -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore pc-store.p12 -validity 3650 -dname "CN=pcstore.niko.de, OU=PC Sale, O=Niko GmbH, L=Nürnberg, S=Bayern, C=DE"`

[Resources](src/main/resources/README.md)

[Testing](src/test/README.md)

[Documentation](documentation/DOCUMENTATION.md)

### Team
| Role             | Name, Nachname | E-mail |
|:-----------------|----------------|--------|
| Architect        |                |        |
| Business analyse |                |        |
| System analyse   |                |        |
| Development      |                |        |
| Testing          |                |        |
| Administration   |                |        |

### TODO list

- [x] One-request creation.
- [x] Add client data.
- [x] Swagger generation migrate from SpringFox (https://springdoc.org/migrating-from-springfox.html).
- [x] Migrate security from Basic to JWT.
- [x] Add /get-token to swagger UI.
- [x] Role hierarchy.
- [x] Integrate OAuth2 security (Keycloak)
  - [ ] Logout and token invalidate
  - [ ] Redirect (Code + redirect url) if not authenticated (https://www.predic8.de/oauth2-beispiel.htm 2)
- [ ] CRON work for archiving.
- [ ] Swagger: ordering in json objects order.
- [ ] Split ApiControllers into service and controller.
- [ ] Migrate to Mapstruct https://mapstruct.org/.
- [ ] Migrate to Spring Boot 3.
  - Springdoc for Spring Boot 3: https://springdoc.org/v2/.

### Others

- debug request-response communication : `-Djavax.net.debug=ssl`
- `mvn dependency:tree`
- `git config core.editor notepad`

###### Links:
- https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
- https://www.baeldung.com/spring-boot-keycloak