FROM openjdk:10-jdk-slim AS build-env
ADD . /app
WORKDIR /app
RUN sh ./mvnw clean package -DskipTests=true

FROM openjdk:10-jre-slim
COPY --from=build-env /app/target/leeroy-app-0.0.1-SNAPSHOT.jar /app/web.jar
WORKDIR /app
CMD ["java", "-XX:MaxRAMPercentage=80", "-jar", "web.jar"]

EXPOSE 8080
