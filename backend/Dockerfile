FROM php:8.2-fpm-alpine

WORKDIR /var/www/app

RUN apk update && apk add \
    nano \
    postgresql-dev # Install PostgreSQL development files

RUN docker-php-ext-install pdo pdo_pgsql

COPY --from=composer:latest /usr/bin/composer /usr/local/bin/composer

USER root
