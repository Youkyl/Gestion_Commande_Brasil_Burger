📦 Projet Java – Gestion des Ressources
🍔 Brasil Burger – L3 ISM (Semestre 1)
📌 Description

Ce projet correspond à la partie Java Console du projet Gestion des Commandes – Brasil Burger.

L’objectif de cette application est de gérer les ressources du restaurant à savoir :

Burgers

Menus

Compléments (frites, boissons)

L’application est développée en Java avec Maven, en utilisant JDBC pour l’accès à une base de données PostgreSQL hébergée sur NeonDB.

⚠️ Conformément au cahier des charges, cette partie ne gère pas les commandes, paiements, livraisons ou statistiques.

🧱 Architecture du projet

Le projet suit une architecture en couches claire et professionnelle :

src/main/java/com/ism/restaurant
│
├── config        → Connexion à la base de données (JDBC)
├── model         → Entités métier (Burger, Menu, Complement, etc.)
├── dao           → Accès aux données (CRUD JDBC)
├── service       → Logique métier
└── view          → Interface console (menus interactifs)

🛠️ Technologies utilisées

Java 17

Maven

JDBC

PostgreSQL

NeonDB (Cloud PostgreSQL)

VS Code

🔐 Sécurité & Configuration

Les informations sensibles ne sont pas stockées en dur dans le code.

La connexion à la base de données utilise des variables d’environnement :

DB_URL
DB_USER
DB_PASSWORD


Ces variables doivent être définies avant l’exécution du projet.

⚙️ Fonctionnalités implémentées
🍔 Gestion des Burgers

Ajouter un burger

Modifier un burger

Archiver un burger (suppression logique)

📋 Gestion des Menus

Ajouter un menu

Un menu est composé d’un burger + frites + boisson

Le prix est calculé automatiquement :

prixMenu = prixBurger + 1500


Modifier un menu

Archiver un menu

🥤 Gestion des Compléments

Ajouter un complément (FRITE ou BOISSON)

Modifier un complément (nom, prix, image)

Archiver un complément

🔒 Les champs type et stock ne sont pas modifiables après la création, conformément aux règles métier.

▶️ Lancer le projet
1️⃣ Prérequis

Java 17 installé

Maven installé

Variables d’environnement configurées

Base de données PostgreSQL accessible

2️⃣ Compilation
mvn clean compile

3️⃣ Exécution
mvn exec:java

📋 Menu principal (console)
===== BRASIL BURGER - GESTION =====
1. Gestion des Burgers
2. Gestion des Menus
3. Gestion des Compléments
0. Quitter


Chaque section dispose de son propre sous-menu (ajout, modification, archivage).

🗄️ Base de données

Base de données partagée avec les projets :

ASP.NET MVC

Symfony

Créée manuellement (sans migration)

Hébergée sur NeonDB

Respect strict du schéma fourni dans la modélisation

📌 Limitations (choix volontaires)

Les fonctionnalités suivantes ne sont pas implémentées dans cette partie Java :

Gestion des commandes

Paiements (Wave / Orange Money)

Livraison

Statistiques

Authentification avancée

Upload d’images

👉 Ces fonctionnalités sont traitées dans les projets C# ASP.NET MVC et Symfony, conformément au cahier des charges.

👤 Auteur

Projet réalisé par :
[Ton Nom]
Étudiant en Licence 3 – ISM
Année académique : 2024 – 2025

✅ Statut du projet

✔️ Fonctionnel
✔️ Conforme au cahier des charges
✔️ Prêt pour soutenance et déploiement