# Gestion_Commande_Brasil_Burger
🍔 Brasil Burger - Gestion de Commandes Restaurant
📋 Description du Projet
Application web et mobile de gestion des commandes et livraisons pour le restaurant Brasil Burger, spécialisé dans la vente de burgers. Le système permet la gestion complète des menus, burgers, compléments, commandes et livraisons.

🎯 Contexte Académique
Niveau : L3 ISM (Licence 3 Ingénierie des Systèmes et Multimédia)
Semestre : 1
Client : Restaurant Brasil Burger
🏗️ Architecture du Projet
Le projet est divisé en 3 applications partageant la même base de données :

1. Java Console 🟢
Gestion des ressources (CRUD) :

Burgers
Menus
Compléments
2. C# ASP.NET MVC 🔵
Fonctionnalités côté Client :

Consultation du catalogue
Commande de menus/burgers
Suivi des commandes
Paiement (Wave/OM)
Authentification client
3. Symfony (PHP) 🟣
Fonctionnalités côté Gestionnaire :

Gestion des commandes
Suivi des commandes
Affectation aux livreurs
Statistiques et rapports
Gestion des zones de livraison
📊 Modélisation
Diagrammes UML
✅ Diagramme de Use Case
✅ Diagramme de Classe
✅ Diagramme de Séquence (conception)
✅ MLD (Modèle Logique de Données)
✅ Maquettes Figma
🗄️ Base de Données
Entités Principales
Burger
id
nom
prix
image
statut (actif/archivé)
Menu
id
nom
image
prix_calcule (somme des composants)
burger_id
boisson_id
frite_id
Complément
id
nom
type (boisson/frite)
prix
image
Client
id
nom
prenom
telephone
email
mot_de_passe
date_inscription
Commande
id
client_id
date_commande
type_consommation (sur place/à emporter/livraison)
statut (en attente/en cours/terminée/annulée)
montant_total
zone_id (si livraison)
livreur_id (si livraison)
LigneCommande
id
commande_id
produit_type (burger/menu)
produit_id
quantite
prix_unitaire
montant_ligne
Paiement
id
commande_id
date_paiement
montant
mode_paiement (Wave/OM)
statut (réussi/échoué)
Zone
id
nom
quartiers (JSON/Text)
prix_livraison
Livreur
id
nom
prenom
telephone
zone_id
Script SQL
sql
-- Disponible dans la branche 'modelisation'
-- Fichier : database/schema.sql
🚀 Fonctionnalités
👨‍💼 Gestionnaire
Gestion des Produits
✅ Ajouter/Modifier/Archiver des burgers
✅ Ajouter/Modifier/Archiver des menus
✅ Ajouter/Modifier/Archiver des compléments
Gestion des Commandes
✅ Lister toutes les commandes
✅ Filtrer par burger/menu
✅ Filtrer par date
✅ Filtrer par état
✅ Filtrer par client
✅ Annuler une commande
✅ Changer l'état (Terminer)
Gestion des Livraisons
✅ Regrouper les commandes par zone
✅ Affecter à un livreur
Statistiques
📊 Commandes en cours du jour
📊 Commandes validées du jour
📊 Recettes journalières
📊 Burgers/Menus les plus vendus
📊 Commandes annulées du jour
👤 Client
Catalogue
✅ Voir tous les burgers disponibles
✅ Voir tous les menus disponibles
✅ Filtrer par burger/menu
✅ Voir détails d'un burger
✅ Voir détails d'un menu
Commande
✅ Commander un burger (avec compléments suggérés)
✅ Commander un menu
✅ Choisir type : sur place/à emporter/livraison
✅ Payer la commande (Wave/OM)
✅ Suivi des commandes
Authentification
✅ Créer un compte
✅ Se connecter
✅ Gérer son profil
📦 Technologies Utilisées
Backend
Java (Console) - JDK 17+
C# ASP.NET Core MVC - .NET 8.0
PHP Symfony - 7.0+
Base de Données
MySQL / PostgreSQL / SQL Server
Frontend
Razor Pages (C# MVC)
Twig (Symfony)
Bootstrap 5
JavaScript/jQuery
Déploiement
Render (https://render.com/)
GitHub (CI/CD)
📂 Structure du Repository
brasil-burger/
├── modelisation/          # Branche modélisation
│   ├── diagrammes/
│   │   ├── use-case.png
│   │   ├── classe.png
│   │   └── sequence.png
│   ├── maquettes/
│   │   └── figma-link.txt
│   └── database/
│       ├── mld.png
│       └── schema.sql
│
├── java/                  # Branche Java
│   ├── src/
│   │   └── com/brasilburger/
│   │       ├── models/
│   │       ├── dao/
│   │       └── Main.java
│   └── README.md
│
├── csharp/                # Branche C#
│   ├── BrasilBurger.Web/
│   │   ├── Controllers/
│   │   ├── Models/
│   │   ├── Views/
│   │   └── wwwroot/
│   └── README.md
│
└── symfony/               # Branche Symfony
    ├── src/
    │   ├── Controller/
    │   ├── Entity/
    │   └── Repository/
    ├── templates/
    └── README.md
🔧 Installation et Configuration
Prérequis
Java JDK 17+
.NET 8.0 SDK
PHP 8.2+ avec Composer
MySQL/PostgreSQL
Git
1. Cloner le Repository
bash
git clone https://github.com/votre-username/brasil-burger.git
cd brasil-burger
2. Configuration Base de Données
bash
# Créer la base de données
mysql -u root -p
CREATE DATABASE brasil_burger;

# Importer le schéma
mysql -u root -p brasil_burger < modelisation/database/schema.sql
3. Configuration Java Console
bash
git checkout java
cd java
# Configurer database.properties
javac -d bin src/com/brasilburger/**/*.java
java -cp bin com.brasilburger.Main
4. Configuration C# MVC
bash
git checkout csharp
cd csharp

# Modifier appsettings.json avec vos credentials DB
dotnet restore
dotnet run
# Accès : https://localhost:7xxx
5. Configuration Symfony
bash
git checkout symfony
cd symfony

# Modifier .env avec vos credentials DB
composer install
php bin/console cache:clear
symfony server:start
# Accès : http://localhost:8000
📅 Planning de Livraison
📌 Livrable 1 : 14/12/2025
✅ Modélisation complète
✅ Projet Java Console (CRUD ressources)
✅ Déploiement Java
📌 Livrable 2 : 20/12/2025
✅ Projet C# ASP.NET MVC (Fonctionnalités Client)
✅ Déploiement C#
📌 Livrable 3 : 30/12/2025
✅ Projet Symfony (Fonctionnalités Gestionnaire)
✅ Déploiement Symfony
📝 Convention de Commits
Chaque fonctionnalité doit avoir son propre commit :

bash
# Exemples de commits
git commit -m "feat: Créer un menu"
git commit -m "feat: Lister les menus"
git commit -m "feat: Modifier un burger"
git commit -m "feat: Archiver un complément"
git commit -m "feat: Commander un burger avec compléments"
git commit -m "feat: Paiement Wave"
git commit -m "feat: Statistiques commandes du jour"
git commit -m "fix: Calcul prix menu"
git commit -m "docs: Mise à jour README"
Préfixes de Commits
feat: - Nouvelle fonctionnalité
fix: - Correction de bug
docs: - Documentation
style: - Formatage, CSS
refactor: - Refactoring code
test: - Ajout de tests
chore: - Maintenance
🌐 Déploiement
Sur Render
1. Java Console
bash
# Créer un Dockerfile
# Déployer via GitHub
2. C# MVC
bash
# Web Service sur Render
# Build Command: dotnet publish -c Release -o out
# Start Command: dotnet out/BrasilBurger.Web.dll
3. Symfony
bash
# Web Service sur Render
# Build Command: composer install --no-dev --optimize-autoloader
# Start Command: symfony server:start --port=$PORT
Variables d'Environnement
env
DATABASE_URL=mysql://user:pass@host:port/dbname
APP_ENV=production
SECRET_KEY=your-secret-key
🧪 Tests
bash
# Java
mvn test

# C#
dotnet test

# Symfony
php bin/phpunit
👥 Équipe de Développement
Chef de Projet : [Nom]
Développeur Java : [Nom]
Développeur C# : [Nom]
Développeur Symfony : [Nom]
Designer UI/UX : [Nom]
📞 Contact
Email : contact@brasilburger.sn
GitHub : https://github.com/votre-username/brasil-burger
Figma : [Lien vers les maquettes]
📄 Licence
Ce projet est réalisé dans un cadre académique - L3 ISM.

🎓 Établissement
[Nom de votre Université/École]
Licence 3 - Ingénierie des Systèmes et Multimédia
Année Académique : 2024-2025

© 2025 Brasil Burger - Tous droits réservés


