# Strava Connection

## Building the Strava Client
1. Download the Swagger Jar : curl http://central.maven.org/maven2/io/swagger/swagger-codegen-cli/2.3.1/swagger-codegen-cli-2.3.1.jar -O swagger-codegen-cli.jar
- [More information About Swagger](https://github.com/swagger-api/swagger-codegen/blob/master/README.md#prerequisites)
2. Clone the [Strata Repo](https://github.com/strava/developers.strava.com/)
3. java -jar ~/swagger-codegen-cli-2.3.1.jar generate -i https://developers.strava.com/swagger/swagger.json -c config/strava-html.json -l java -o generated/java
4. cd generated/java
5. Read README.md
6. mvn clean install (install to local repo)
