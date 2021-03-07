# Spring Security Library

1) Build executable .jar file
2) Create a file with name "Dockerfile" and put it to the same folder

Put following in "Dockerfile":

```python
FROM java:8
ADD jarname.jar jarname.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "jarname.jar"]

```

Then we build docker with the name "app-test":
```python
sudo docker build -t app-test .
```

If we want to launch this docker, then we can use following command:
```python
sudo docker run -d -p 8085:8085 app-test
```
This command will launch docker in the background and "process hash" will be returned to the console

Another way to launch docker:
```python
sudo docker run -p 8085:8085 app-test
```

Default way to launch executable. This allows us to see our application,
but console will be blocked.

Try to get your secret on http://localhost:8085/secret


