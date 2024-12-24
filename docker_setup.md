# Docker Setup

## Postgres

1. Pull latest Postgres image from Docker Hub.

```shell
docker pull postgres
```

2. Run image using environment variables

```shell
docker run --name prospyr_postgres -e POSTGRES_PASSWORD=root -p 5433:5432 -d postgres
```

3. Enter into Docker container using `exec` command

```shell
docker exec -it prospyr_postgres bash
```

4. To enter into docker container, login via `postgres` user

```shell
su - postgres
```

5. Type `psql` to work using interactive terminal in the Postgres

```shell
psql
```

6. Use `\du` command to list all database users

```shell
\du
```

7. Create a *superuser* with creation roles

```shell
CREATE ROLE prospyr_user with login SUPERUSER PASSWORD 'prospyr_user';
```

8. Add more privileges to the user

```shell
ALTER USER prospyr_user WITH CREATEDB CREATEROLE;
```

9. Quit from database connection using psql command `\q` and then type `exit` to logged out `postgres` user. And then type another time `exit` to leave out from docker container.

```shell
\q
exit
exit
```

10. Now, run the container using newly created user in step 7

```shell
docker run --name prospyr_postgres -e POSTGRES_PASSWORD=prospyr_user -e POSTGRES_USER=prospyr_user -p 5433:5432 -d postgres
```


