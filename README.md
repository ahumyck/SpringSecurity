# Json Web Token Vulnerability

1) Install docker
2) Build docker "web-service"  with following command

```python
sudo docker build -t web-service .
```

3) Launch docker:
```python
sudo docker run -p 8080:8080 web-service
```

4)Try to get your secret on http://localhost:8080


