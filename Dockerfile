FROM openjdk:8-jdk-alpine AS build-env
ADD . /app
WORKDIR /app
RUN ./mvnw clean package -DskipTests=true

FROM openjdk:8-jre-alpine
COPY --from=build-env /app/target/leeroy-app-0.0.1-SNAPSHOT.jar /app/web.jar
WORKDIR /app
CMD ["java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-jar", "web.jar"]

EXPOSE 8080
