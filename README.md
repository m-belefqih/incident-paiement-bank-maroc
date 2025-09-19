# Application de traitement et de validation des fichiers incidents de paiement (CTR, CLI, CPT, IPS) – Système CIP (Bank Al-Maghrib)

<div align="center">
  <img src="al-akhdar-bank.gif" alt="Al Akhdar Bank" width="1200">
</div>

--- 

## 👥 Contributeurs

Ce projet a été réalisé par :

- **Mohammed BELEFQIH**
- **Hamza ASSERMA**
- **Inass TEMMAR**

**Superviseure :** Mme. Safia BOUAGRA

## Contexte du projet

Cette application a été développée pour **Al Akhdar Bank** dans le cadre du **Système CIP (Clearing Interbancaire de Paiements)** de Bank Al-Maghrib. Elle vise à automatiser et sécuriser le processus de traitement des fichiers d'incidents de paiement.

## 🎯 Objectifs principaux

L'application permet de :

1. **Importer et valider automatiquement** les fichiers d'incidents de paiement aux formats :
    - `CTR` Fichier de Compte-Rendu de Traitement
    - `CLI` Fichier des clients (personnes physiques `P` et morales `M`)
    - `CPT` Fichier des comptes et liens (`C` pour comptes, `L` pour les liaisons client-compte)
    - `IPS` Fichier des incidents de paiement (déclarations I, régularisations R, annulations A, infractions N…)

2. **Générer des comptes-rendus détaillés** (`CTR`) indiquant :
    - Les erreurs détectées lors de la validation
    - Le statut d'acceptation ou de rejet des fichiers
    - Les codes d'erreur spécifiques selon les règles métier

3. **Assurer une traçabilité complète** avec :
    - Historique des imports et traitements
    - Horodatage de toutes les opérations
    - Suivi des statuts de traitement

4. **Gérer les utilisateurs et les droits d'accès** selon deux niveaux :
    - **Utilisateurs standard** : traitement et validation
    - **Administrateurs** : gestion complète du système

## 🛠️ Technologies utilisées

- **Backend** : Java, Spring Boot
- **Frontend** : Thymeleaf (moteur de templates), Tailwind CSS, Flowbite (UI components)
- **Base de données** : PostgreSQL, Spring Data JPA
- **Sécurité** : Spring Security
- **Tests des API** : Postman
- **Build** : Maven
- **Environnements de développement** : IntelliJ IDEA, VS Code
- **Gestion de versions** : Git, GitHub

## 📌 Installation et configuration

### Prérequis

Pour exécuter ce projet, assurez-vous d'avoir installé :

- [x] **Java Development Kit (JDK) 17 ou supérieur**
- [x] **Apache Maven 3.6 ou supérieur**
- [x] **PostgreSQL 12 ou supérieur**

### Étapes pour lancer le projet

1. **Cloner le repository**

```bash
git clone https://github.com/m-belefqih/incident-paiement-bank-maroc.git
```

2. Créer une base de données **PostgreSQL** vide nommée `alakhdarbank`.

3. Mettre à jour le fichier `application.properties` avec vos identifiants **PostgreSQL** :

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/alakhdarbank
spring.datasource.username=your_username
spring.datasource.password=your_password
```

4. Accéder au dossier du projet :

```bash
cd incident-paiement-bank-maroc
```

5. Compiler et construire le projet :

```bash
mvn clean install
```

6. Lancer l’application une première fois pour que les tables soient créées automatiquement dans la base de données :

```bash
mvn spring-boot:run
```

7. Arrêter l’application après le démarrage.

8. Importer les fichiers CSV dans la base de données :

- Importer le fichier `alakhdarbank/database.csv` dans la table `error_code`.
- Importer le fichier `alakhdarbank/users.csv` dans la table `users`.

9. Exécuter la requête suivante dans la base de données pour éviter les erreurs de clé dupliquée lors de l’insertion de nouveaux utilisateurs :

```bash
SELECT setval('users_id_seq', (SELECT MAX(id) FROM users));
```

10. Relancer l’application :

```bash
mvn spring-boot:run
```

11. Ouvrir l'application dans votre navigateur

```bash
http://localhost:8080/alakhdarbank/
```

### ⚠️ Notez bien que

Pour vous connecter à la page de login de l’application, utilisez un nom d’utilisateur présent dans la table `users.csv` que vous avez importée. Le mot de passe pour tous les utilisateurs est: `admin`.