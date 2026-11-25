# DevPlatform — projet SOA (académique)

Trois briques, une seule source de données :

```
interface (React / Vite)
        |  REST JSON
        v
api-gateway (Jersey + Grizzly, port 9000)        <- futur service d'authentification
        |  SOAP / JAX-WS
        v
gestion-projets-service (JAX-WS + JPA/Hibernate, port SOAP 8081)
        |
        v
PostgreSQL (docker-compose)
```

Aucun framework applicatif n'est utilisé côté Java : démarrage direct (`main`), Jersey/Grizzly pour le REST,
JAX-WS pour le SOAP, Hibernate pour la persistance.

## Décision : où sont créées les tables ?

Les tables sont créées **dans `gestion-projets-service`**, par Hibernate à partir des entités JPA
(`hibernate.hbm2ddl.auto=update`). C'est le service propriétaire du domaine « projets ».

- La passerelle n'a **aucun accès à la base** : elle ne fait que traduire REST ↔ SOAP.
- Aucun service supplémentaire n'a été créé pour la base : ce serait un découpage inutile pour un projet académique.
- `gestion-projets-service/src/main/resources/db/schema.sql` documente le mapping exact avec `devplatform.xsd`
  (il n'est pas exécuté automatiquement pour éviter de doubler Hibernate).

| XSD | Table / colonne |
| --- | --- |
| `DevPlatform/@organisation` | `plateforme.organisation` |
| `Projet/@id`, `@lang`, `Nom`, `Statut` | `projets.id`, `lang`, `nom`, `statut` |
| `Developpeur/@id`, `@role`, `Nom`, `Email` | `developpeurs.id`, `role`, `nom`, `email`, `projet_id` |
| `Tache/@id`, `@priorite`, `@type`, `Titre`, `TempsEstime`, `Etat` | `taches.id`, `priorite`, `type`, `titre`, `temps_estime`, `etat`, `projet_id` |

## Démarrage

```bash
# 1. base de données
cp .env.example .env    # facultatif ; valeurs par défaut déjà définies
docker compose up -d

# 2. service métier (SOAP + JPA) : construire puis lancer le jar
cd gestion-projets-service
mvn package
java -jar target/gestion-projets-service-1.0-SNAPSHOT.jar
# WSDL : http://localhost:8081/ws/projets?wsdl

# 3. passerelle REST
cd ../api-gateway
mvn package
java -jar target/api-gateway-1.0-SNAPSHOT.jar
# API : http://localhost:9000/api/projects

# 4. interface
cd ../interface
npm install
npm run dev
# http://localhost:5173
```

Service métier : `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USER`, `DB_PASSWORD`, `SOAP_PORT`, `SOAP_PATH`, `ORGANISATION`.
Passerelle : `GATEWAY_PORT` (ou `PORT`) et `PROJETS_SOAP_ENDPOINT`.

Si le port 5432 est déjà pris : `POSTGRES_PORT=5433 docker compose up -d`, puis
`DB_PORT=5433 java -jar target/gestion-projets-service-1.0-SNAPSHOT.jar`.

Le CORS est accepté pour toutes les origines : les en-têtes `Access-Control-Allow-*` sont écrits
directement par `ApiCorsFilter` (Jersey), sans configuration de framework. Le front utilise
`VITE_API_BASE_URL` (défaut `http://localhost:9000/api`, c'est-à-dire la racine des routes REST).

Un jeu de données de démonstration (2 projets) est inséré au premier démarrage uniquement.

## API REST de la passerelle

| Méthode | Chemin | Usage |
| --- | --- | --- |
| `GET` | `/api/projects` | Liste des projets |
| `GET` | `/api/projects/{id}` | Détail (équipe + tâches) |
| `POST` | `/api/projects` | Créer un projet |
| `POST` | `/api/projects/{id}/tasks` | Ajouter une tâche |
| `PUT` | `/api/projects/{id}/tasks/{taskId}` | Changer l'état d'une tâche |

Erreurs JSON uniformes : `{"code": "NOT_FOUND", "message": "..."}`.
Les `ServiceFault` SOAP sont traduites en 400 / 404, les pannes SOAP en 502,
et toute erreur non prévue en 500 sans fuite de stacktrace.

## Interface

- `src/app/` : routes (`react-router-dom`) et coque de l'application.
- `src/components/` : composants visuels réutilisables (Sidebar, Badge, Modal, TaskTable…).
- `src/hooks/` : `useAsync`, `useMutation`, `useProjects`, `useProject`, hooks de création/mise à jour.
- `src/services/` : unique couche d'appel HTTP ; `src/lib/apiClient.ts` gère erreurs et en-têtes.
- `src/auth/` : seam d'authentification. Aujourd'hui sans session (`user: null`) ; le jeton est lu
  depuis `localStorage` et envoyé en `Authorization`. Le futur service d'authentification n'aura qu'à
  alimenter `AuthProvider` et ajouter ses routes, sans toucher aux pages.

Design volontairement sobre, inspiré de Jira (navigation latérale, lozenges de statut, tableaux,
boutons bleus) : pas de template « AI », pas de bibliothèque UI supplémentaire, pas de fonctionnalités hors sujet.
