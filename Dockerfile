FROM openjdk:11
RUN gradlew build
ADD /build/libs/securing-web-0.0.1-SNAPSHOT.jar jwt_task.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "jwt_task.jar"]