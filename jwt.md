Generate RSA keys for JWT

```shell
cd src/main/resources/jwt
openssl genpkey -algorithm RSA -out app.key -outform PEM
openssl rsa -pubout -in app.key -out app.pub
```