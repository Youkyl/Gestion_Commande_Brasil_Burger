FROM php:8.4-cli

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
        sodium \
    && rm -rf /var/lib/apt/lists/*

# Installer Composer
COPY --from=composer:2 /usr/bin/composer /usr/bin/composer

# Variables Composer
ENV COMPOSER_ALLOW_SUPERUSER=1
ENV COMPOSER_MEMORY_LIMIT=-1

WORKDIR /app

# Copier les fichiers Composer
COPY composer.json composer.lock symfony.lock ./


# Installer dépendances
RUN composer install \
    --no-dev \
    --no-scripts \
    --no-interaction \
    --optimize-autoloader \
    --prefer-dist

# Copier le reste du projet
COPY . .

# Variables d'environnement Symfony
ENV APP_ENV=prod
ENV APP_DEBUG=0

# Générer le cache et assets
RUN php bin/console cache:clear --env=prod --no-warmup && \
    php bin/console cache:warmup --env=prod && \
    php bin/console importmap:install && \
    php bin/console assets:install public --env=prod --symlink --relative || \
    php bin/console assets:install public --env=prod

# Permissions Symfony
RUN mkdir -p var/cache var/log && \
    chmod -R 777 var

EXPOSE 10000

CMD ["php", "-S", "0.0.0.0:10000", "-t", "public", "public/index.php"]
