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
  - `docker run --name postgresql-dev -p 5432:5432 -e POSTGRES_PASSWORD=smartpassword -d postgres`


- Docker console commands for prepare postgre database:
  - `bash` - Start bash console.
  - `su postgres` - Switch user to postgres.
  - `psql` - start postgres console.
  - `CREATE USER pcstoreadmin WITH PASSWORD '6tfgv6zghb6';` - create a database user.
  - `CREATE DATABASE smart;` - create a database schema.

### Production
Will be started with Postgres as database and security (TLS and authorization).

`--spring.profiles.active=prod`

maven command: `mvn clean package -Pprod`

## Security

#### Generate a TLS-certificate:

`/c/Program\ Files/Java/jdk-16.0.1/bin/keytool.exe -genkeypair -alias smart -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore pc-store.p12 -validity 3650 -dname "CN=pcstore.niko.de, OU=PC Sale, O=Niko GmbH, L=Nürnberg, S=Bayern, C=DE"`

[Resources](src/main/resources/README.md)

[Testing](src/test/README.md)

[Documentation](documentation/DOCUMENTATION.md)

### Team
| Role             | Name, Nachname | Email |
|:-----------------|----------------|-------|
| Architect        |                |       |
| Business analyse |                |       |
| System analyse   |                |       |
| Development      |                |       |
| Testing          |                |       |
| Administration   |                |       |

### TODO list

- [x] One-request creation.
- [ ] CRON work for archiving.
- [ ] Swagger: ordering in json objects order.
- [x] Add client data
- [x] Database initialization for tests
- [ ] Split ApiControllers into service and controller 

### Others

debug request-response communication : `-Djavax.net.debug=ssl`

