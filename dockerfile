FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/valonis-*.jar valonis.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/valonis.jar"]