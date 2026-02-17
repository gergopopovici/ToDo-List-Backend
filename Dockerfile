FROM gradle:8.5-jdk21 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN ./gradlew :pgim2289-spring:build -x test -x violations

FROM eclipse-temurin:21-jre
EXPOSE 8080
COPY --from=build /home/gradle/src/pgim2289-spring/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]