FROM php:8.3-cli

# Dépendances système + extensions PHP complètes Symfony
RUN apt-get update && apt-get install -y \
    git \
    unzip \
    libpq-dev \
    libzip-dev \
    libicu-dev \
    libpng-dev \
    libjpeg-dev \
    libfreetype6-dev \
    libonig-dev \
    libxml2-dev \
    libcurl4-openssl-dev \
    libsodium-dev \
    && docker-php-ext-configure gd --with-freetype --with-jpeg \
    && docker-php-ext-install \
        pdo \
        pdo_pgsql \
        zip \
        intl \
        gd \
        mbstring \
        ctype \
        iconv \
        xml \
        curl \
        sodium

# Installer Composer
COPY --from=composer:2 /usr/bin/composer /usr/bin/composer

WORKDIR /app

# Copier les fichiers Composer
COPY composer.json composer.lock ./

# Installer dépendances (plateforme strictement compatible)
RUN composer install \
    --no-dev \
    --no-scripts \
    --no-interaction

# Copier le reste du projet
COPY . .

# Cache Symfony (tolérant DB absente)
RUN php bin/console cache:clear --env=prod || true

# Permissions Symfony
RUN mkdir -p var/cache var/log && chmod -R 777 var

EXPOSE 10000

CMD ["php", "-S", "0.0.0.0:10000", "-t", "public"]