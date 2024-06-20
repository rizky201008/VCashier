FROM php:8.3-fpm

ARG user
ARG uid

RUN apt-get update && apt-get install -y \
    curl \
    libpng-dev \
    libxml2-dev \
    zip \
    unzip \
    nano \
    postgresql-common \
    libpq-dev # Add this line

RUN docker-php-ext-install pdo pdo_pgsql

COPY --from=composer:latest /usr/bin/composer /usr/local/bin/composer

# Create system user to run Composer and Artisan Commands
RUN useradd -G www-data,root -u $uid -d /home/$user $user
RUN mkdir -p /home/$user/.composer && \
    chown -R $user:$user /home/$user

USER $user

WORKDIR /var/www/app
