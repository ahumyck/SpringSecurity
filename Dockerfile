FROM java:8
FROM gradle:6.8.3-jdk11 AS build
ADD . /home
WORKDIR /home
RUN gradle build
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/build/libs/securing-web-0.0.1-SNAPSHOT.jar"]
