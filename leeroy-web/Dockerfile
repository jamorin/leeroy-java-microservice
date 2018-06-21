FROM openjdk:8-jre-alpine

ADD target/leeroy-web-0.0.1-SNAPSHOT.jar /app.jar

CMD ["java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-jar", "/app.jar"]

EXPOSE 8080
