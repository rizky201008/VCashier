FROM php:8.2-fpm-alpine

WORKDIR /var/www/app

RUN apk update && apk add \
    curl \
    libpng-dev \
    libxml2-dev \
    zip \
    unzip \
    nano \
    postgresql-dev # Install PostgreSQL development files

RUN docker-php-ext-install pdo pdo_pgsql # Install PDO extensions for MySQL and PostgreSQL

RUN apk --no-cache add nodejs npm

COPY --from=composer:latest /usr/bin/composer /usr/local/bin/composer

USER root

RUN chmod -R 777 /var/www/app
