# Json Web Token Vulnerability

1) Build executable .jar file
2) Create a file with name "Dockerfile" and put it to the same folder

Put following in "Dockerfile":

```python
FROM java:8
FROM gradle:6.8.3-jdk11 AS build
ADD . /home
WORKDIR /home
RUN gradle build
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/build/libs/securing-web-0.0.1-SNAPSHOT.jar"]

```

Then we build docker with the name "app-test":
```python
sudo docker build -t web-service .
```

Launch docker:
```python
sudo docker run -p 8080:8080 web-service
```

Default way to launch executable. This allows us to see our application,
but console will be blocked.

Try to get your secret on http://localhost:8080


