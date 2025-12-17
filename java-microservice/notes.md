# Code de retour HTTP:
* 1xx Information
* 2xx Succès
* * 200 OK
* * 201 création
* * 204 équivalent de void en C
* 3xx Redirection
* * Point déplacé de manière permanante
* 4xx Erreur venant de la requête du client
* * 400 impossible de comprendre la requête
* * 401 Non authorizé (mauvaise identification, etc)
* * 403 Le serveur refuse d'autoriser la requête
* * 404 Not Found
* 5xx Erreur venant du serveur
* * 500 erreur interne au serveur

# Types
* get  -> lire une ressource depuis le serveur sans la modifier
* post -> créer une ressource dans le serveur
* put  -> mettre à jour une ressource sur le serveur
* delete -> Supprimer une ressource sur le serveur

# Spring Boot
Spring + configuration + embedded server

Les objets géré par Spring s'appellent des 'beans'

## Architecture en couche
* Presentation Layer -> frontend
* Service Layer -> communication entre presentation layer et data layer. Contient la logique de l'application (business logic)
* Data Access Layer -> accès aux bases de données

Browser <-> Controller <-> Service <-> Repository <-> database

## Requête
servlet dispatcher permet de dispatcher les requête du navigateur il est le coeur de toute applciation Spring MVC. Automatiquement configuré par Spring Boot

## Notes
Le contenu du fichier application.properties peut être spécifié en ligne de commande (utile j'imagine pour ne pas mettre des mots de passe dans les sources de l'application)
