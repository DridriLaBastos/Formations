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
* Presentation Layer -> Presents data and the application features to the user. Controllers exist in this layer
* Service Layer -> Contains business logic.
* Data Access Layer -> Contains repository class. Manage access to the database

Browser <-> Controller <-> Service <-> Repository <-> database

## Requête
servlet dispatcher permet de dispatcher les requête du navigateur il est le coeur de toute applciation Spring MVC. Automatiquement configuré par Spring Boot

## Configuration
L'application spring boot est configuré dans le fichier `application.properties`.
Les valeurs de configuration peuvent aussi être ajoutée en ligne de commande (retrouver l'exemple de comment on fait)
Le fichier peut aussi être écrit au format YAML

Ces deux fichiers sont équivalent

```properties
spring.application.name=ecom-application

spring.h2.console.enabled=true
spring.datasource.url=jdbc:h2:mem:test
```

```yml
spring:
    application:
        name: ecom-application
    h2:
        console:
            enabled: true
    datasource:
        url: jdbc:h2:mem:test
```

__Remarques__
* Avec IntelliJ quand les dépendances sont changées il faut effectuer la manoeuvre suivante : Réglage -> Build, Execution, Deplaiyement -> Compiiler -> Annotation Processor -> Sélectionner Obtain processors from project classpath

_Questions_:
* Dans le cas où le fichier de configuration est écrit en YAML est-ce qu'on peut toujours passer une valeur de configuration avec la même syntaxe en ligne de commande ?

# Liens Utiles
* Logiciel de debugage d'API : https://www.postman.com/
* Configuration d'un projet Spring boot : https://start.spring.io/
* Json format vérificateur : https://jsonlint.com/

# Troubleshooting
* Vu dans windows: dans postman ajouter dans l'en tête la clef "Content-Type": "application/json"

# Notes
## Section 5
* JPA : technologie permettant de transcrire une classe de donnée et leur relation en table et requêtes SQL

## Section 6
### Model Data Transfer Object (DTO)
Permet de faire communiquer des sous-sytème entre eux
(c'est ce que je voulais faire au début en trouvant bizarre qu'il faille envoyer toutes les informations. Encore une fois je me retrouve à avoir la bonne idée tout de suite et ensuite à me rendre compte qu'il y a un design patter associé)

### JPA code parsing
* JPA peut automatiquement créer les reqêtes nécessaire en analysant le nom des méthodes d'un repository
* `findBy<FieldName><Valeur>` -> requête SELECT sur les champs qui ont la valeur demandé
* `@Query(<string>)` comme attribue de fonction permet de spécifier une reqûete JP QL pour faire une requête personnalisée dans la base de donnée en appelanr la fonction

Questions:
* Les conversions de types ne devraient pas dans l'idéal se faire dans le layer repository ?
* Pourquoi le type de l'ID est passé en String dans le UserResponse ? Pour agnostique au type de l'ID utilisé en interne ? Est-ce qu'il ne serait pas plus intéressant de définir un type (équivalent d'un typedef en C) et de garder le typage dans la classe ?
* Le prix total dans un cart ne peut pas être générer automatiquement en SQL quand on ajoute un item ?
* Pourquoi ne pas faire un cartItemResponse pour l'api permettant de récupérer les éléments d'un cart item ?
* Pourquoi EUserRole n'a pas l'annotation Enumerated mais order status l'a ?
* Pourquoi on a besoin de préciser le OneToMany dans la class Order s'il y a déjà le ManyToOne dans la classe OrderItem ?

## Section 7 - Spring Boost Actuators
* Spring Boost Actuators : built-in production ready features to monitor an dmanage the application
* * Ils sont customisable -> on peut créer ses propes endpoints

Actuator généralement utilisé:
* /health: https://docs.spring.io/spring-boot/api/rest/actuator/health.html
* /info: https://docs.spring.io/spring-boot/api/rest/actuator/info.html
* /metrics: https://docs.spring.io/spring-boot/api/rest/actuator/metrics.html
* /loggers: https://docs.spring.io/spring-boot/api/rest/actuator/loggers.html
* /beans
* /shutdowns

### Health
Contrôlé dans le fichier de configuration avec la valeur `management.endpoints.endpoint.health.show-details` qui peut prendre les valeurs
* `never` (default)
* `when-authorized`
* `always`

Documentation : https://docs.spring.io/spring-boot/reference/actuator/endpoints.html#actuator.endpoints.health

### Info
N/A

### Logger
Pour changer dynamiquement le niveau de log encoyer une POST à l'adresse `<application>/actuator/loggers/<module>`

avec en body :
```json
{
    "configuredLevel": "<LEVEL>"
}
```