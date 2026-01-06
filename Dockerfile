FROM php:8.2-cli

# Dépendances système
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
    && docker-php-ext-configure gd --with-freetype --with-jpeg \
    && docker-php-ext-install \
        pdo \
        pdo_pgsql \
        zip \
        intl \
        gd \
        mbstring

# Installer Composer
COPY --from=composer:2 /usr/bin/composer /usr/bin/composer

WORKDIR /app

# Copier composer en premier (cache Docker)
COPY composer.json composer.lock ./

# Désactiver les scripts auto Symfony pendant le build
RUN composer install \
    --no-dev \
    --no-scripts \
    --no-interaction \
    --ignore-platform-reqs

# Copier le reste du projet
COPY . .

# Lancer les scripts Symfony manuellement
RUN php bin/console cache:clear --env=prod || true

# Permissions Symfony
RUN mkdir -p var/cache var/log && chmod -R 777 var

EXPOSE 10000

CMD ["php", "-S", "0.0.0.0:10000", "-t", "public"]