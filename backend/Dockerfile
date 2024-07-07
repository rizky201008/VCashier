FROM php:8.3.9RC1-fpm-alpine

LABEL author="Rizky Agung Prayogi" company="Vixiloc" website="https://vixiloc.com"

WORKDIR /var/www/app

RUN apk update && apk add \
    nano \
    postgresql-dev \
    unzip

RUN docker-php-ext-install pdo pdo_pgsql

COPY --from=composer:latest /usr/bin/composer /usr/local/bin/composer

# Salin file composer.json dan composer.lock
COPY composer.* .

# Jalankan composer install
RUN composer install --no-scripts --no-autoloader

# Salin seluruh kode aplikasi ke dalam image
COPY . .

# Jalankan autoloader
RUN composer dump-autoload --optimize

# Ubah hak akses direktori (opsional)
RUN chown -R www-data:www-data /var/www/app/storage

USER www-data
