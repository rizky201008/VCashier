FROM php:8.3.9RC1-fpm-alpine

LABEL author="Rizky Agung Prayogi" company="Vixiloc" website="https://vixiloc.com"


RUN apk update && apk add \
    nano \
    postgresql-dev \
    unzip

RUN docker-php-ext-install pdo pdo_pgsql

WORKDIR /var/www/app

COPY . .

COPY --from=composer:latest /usr/bin/composer /usr/local/bin/composer

# Jalankan composer install
RUN composer install

RUN php artisan storage:link

# Ubah hak akses direktori (opsional)
RUN chown -R www-data:www-data /var/www/app/storage

USER www-data
