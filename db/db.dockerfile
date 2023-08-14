FROM postgres:13-alpine3.16

COPY ./db_init.sql /docker-entrypoint-initdb.d
