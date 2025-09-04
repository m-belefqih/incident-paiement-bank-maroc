## ✅ 🎯 Titre du projet

> **Développement d’une application de gestion et de validation des fichiers incidents de paiement (CLI, CPT, IPS) destinés au système CIP (Bank Al-Maghrib)**

---

## 🧱 L’idée générale de notre projet

Tu vas créer une app web qui permettra à la banque de :

- **Charger les données client / compte / incident**
- **Générer les fichiers au bon format (CLI, CPT, IPS)**
- **Valider les fichiers** (structure correcte, erreurs détectées)
- **Afficher les erreurs en clair**
- **Envoyer les fichiers à BAM** (optionnel si autorisé)
- **Lire les comptes-rendus (CTR)** et afficher les anomalies

Le tout dans une **architecture sécurisée**, avec un **déploiement automatisé** grâce au **CI/CD** et à la conteneurisation **Docker**.

### 👥 Types d’utilisateurs

| Utilisateur | Rôle |
|----------------|-------------------------------------------|
| **Client** | test .... |
| **Administrateur** | test ... |

### 📌 Fonctionnalités par user

#### 1) Client

- Se connecter / Sé déconnecter
- Consulter les informations personnelles
- Voir le solde de ses comptes
- Consulter l’history des transactions
- Demander un chéquier
- Soumettre une demande de financement
- Suivre le statut de ses demandes
- Contacter l'agence (Système de messagerie)

#### 2) Administrateur

- Gérer les comptes agents et clients
- Définir les droits d’accès
- Superviser le système (logs, statistiques)
- Configurer les paramètres de sécurité

---

## **🛠️ Technologies et Outils**

| Catégorie                       | Technologies / Outils     |
|---------------------------------|---------------------------|
| **Backend**                     | Spring Boot (Java)        |
| **Frontend**                    | Thymeleaf, Tailwind CSS   |
| **Base de données**             | PostgreSQL                |
| **Sécurité / Authentification** | Spring Security, Keycloak |
| **Gestion de versions**         | Git, GitHub               |
| **CI/CD**                       | GitHub Actions            |
| **Conteneurisation**            | Docker                    |
| **Modélisation**                | UML                       |
| **Autres**                      | Postman API, Flowbite     |
| **IDE**                         | VS Code, Intellij IDEA    |

### ✅ Architecture Backend

- **Contrôleurs REST** pour recevoir les requêtes du frontend
- **Services** pour la logique métier (validation, règles)
- **DAO/Repositories** pour communiquer avec la base PostgreSQL

### ✅ Architecture Frontend

- Génération de pages HTML dynamiques avec **Thymeleaf**
- Mise en forme moderne et responsive avec **Tailwind CSS**

### ✅ Sécurité

- Authentification et gestion des rôles avec **Keycloak** et/ou **Spring Security**
- Permissions pour contrôler l’accès aux fonctionnalités selon le rôle (client, admin)


### ✅ CI/CD et Déploiement

- **GitHub Actions** : pipeline automatisé pour compiler, tester et déployer
- **Docker** : conteneurisation de l’application pour un déploiement reproductible

### ✅ Conception et Modélisation

- Diagrammes **UML** pour illustrer :
  - Cas d’utilisation
  - Modèles de données
  - Diagramme de classes
  - Diagramme de séquence

--- 
## 📌 Remarques

- **CIP** : Système Central des Incidents de Paiement
- **CLI** : Fichier des clients (personnes physiques `P` et morales `M`)
- **CPT** : Fichier des comptes et liens (`C` pour comptes, `L` pour les liaisons client-compte)
- **IPS** : Fichier des incidents de paiement (déclarations I, régularisations R, annulations A, infractions N…)
- Recevoir un compte-rendu (fichier **CTR**) indiquant les **erreurs** ou l'**acceptation**

## à noter que 
- Le fichier `database.csv` doit être importé après la création de la base de données **alakhdarbank**
