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
* /beans: https://docs.spring.io/spring-boot/api/rest/actuator/beans.html
* /shutdowns: https://docs.spring.io/spring-boot/api/rest/actuator/shutdown.html

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

### Shutdown

Pour l'activer il faut envoyer une POST request à l'adresse `<application>/actuator/shutdown`

## Questions:
* Exposer les actuators à internet est dangereux. Comment sélectionner les end-points qui sont exposer à internet ou pas ?

# Section 8 : Docker for Spring Boot

* Utilisation de Paketo Buildpacks pour créer les images Docker qui implémente la spécifiqation Cloud Native Buildpacks
* Command : `./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=<nom de l'image docker>`

# Section 9 : PostgreSQL

## Configuration
Penser à créer la base de donnée configurée dans le fichier de configuration de l'application

Le mot de passe pour se connecter à la base de donnée peut être passé par une variable d'environnement lors de l'exécution de docker avec le paramètre `-e VAR=<valeur>`

## Network
Les containeurs peuvent communiquer à traver un réseau docker
* Création d'un réseau: `docker network create <name>`
* Dans docker run ajouter le paramètre `--network <name>` pour connecter les containers au même résuea
* Les noms du conteneur sur le réseau correspondent à leur nom :
* * Si 2 conteneurs `a` et `b` sont connectés au même réseau `docker exec a ping b` permet de pinger le conteneur `b` depuis le conteneur `a`

# Section 12

**MongoDB ne travail qu'avec des String pour les ID**

Certains sites proposent l'utilisation de base de donnée gratuitement (service forcément limité)
* MongoDB : https://cloud.mongodb.com/
* Postgres : https://neon.com/

Possibilité de formater la sortie sql de l'application spring avec le réglage suivant :
`spring:datasaource:jpa:properties:hibernate:format_sql: true`

# Section 13 : Spring Boot Configuration

## Environment Management
* Spring boot profiles : Différentes configuration selon le profile d'exécution (prod, test, dev, ...)

### Récupérer les variables de configuration

```java
@Value("${scope1.scope2. ... .name}")
private String var;
```

La valeur de la variable de configuration `scope1.scope2 ... .name` sera mis dans la variable var.
**Attention** : Erreur si la variable n'est pas renseignée dans le fichier de configuration

### Créer des profiles

1. Création du fichier de configuration `application-<profile>.yaml`
1. Création du profile dans le fichier de configuration par défaut `application.yaml`:

```yaml
spring:
  profiles:
    active: <profile>
```

Chargement automatique du fichier de configuration `application-<profile>.yaml` au redémarrage de l'application

_Remarque_: Ce paramètre peut être passé en ligne de commande

### @Value
Utile pour intégrer des valeurs depuis le fichier de configuration, la ligne de commande, des propriétés du système

* Assignation d'une valeur par défaut si non présente dans le fichier de configuration : `@Value("${scope1.scope2. ... .name:<default-value>}")`
* Assignation d'une variable d'environnement : `@Value("${<VAR_NAME>[:<default-value>]}")`
* Substitution d'une variable d'environnement : `<ENV1>_<ENV2>...<ENVn>` est mappé en une configuration `<env1>.<env2>...<envn>`
* Ligne de commande : `java -jar <app_jar_path> --<scope1>.<scope2> ... .<name>=<value>`
* Lava System Properties : `java -jar -D<scope1>.<scope2> ... .<name>=<value> <app_jar_path>`

### .env file

Définir les variables de configuration dans un fichier `.env`:

```conf
<VAR1ENV1_VAR1ENV2...VAR1ENVn>=value1
<VAR2ENV1_VAR2ENV2...VAR2ENVn>=value2
...
<VARmENV1_VARmENV2...VARmENVn>=valuem
```

Les valeurs seront disponible dans l'applciation _**dans la formation le fichier .env est donné en configuration des variables d'environnements dans IntelliJ -> comment le faire en vrai ?**_

### Configuration Order

1. Ligne de commande
1. Java system properties : 
1. OS environment variable
1. fichier de configuration
1. config server
1. attributed default value

## Security and Sensitive Data

Si la variable d'environnement `ENV1_ENV2` est définie, dans le fichier de configuration sa valeur peut être récupérée avec
```yaml
scope1:
    scope2:
    ...
        <name>: ${ENV1_ENV2}
```

## Consistency and Centralization

### TypeSafety with @ConfigurationProperties

L'annotation `@ConfigurationProperties` permet de mapper un fichier de configuration avec des variables Java en applicant une vérification de type

```Java
@Configuration
@ConfigurationProperties(prefix = "<scope1>.<scope>... .<scopen>")
@Data //To automatically generate getters and setters
public class <ClassName> {
    private <type1> <name1>;
    private <type2> <name2>;
    ...
    private <typen> <namen>;
}
```

```yaml
scope1:
  scope2:
  ...
    scope3:
      name1: <valeur1>
      name2: <valeur2>
      ...
      namen: <valeurn>
```

### Centralization with config server

Spring Cloud Config Server
* Storing configurations
* Serving configurations
* Refreshing configurations
* Easy integration with Spring Boot
* Support for different environments
* Encryption & Decryption

Accessing the file : 

Dans le fichier `application.yaml` du serveur ajouter l'url contenant le fichier de configuration.

Exemple pour un fichier local :

```yaml
spring:
  cloud:
    config:
      server:
        native:
          searchLocations:
            - <chemin1>
            - <chemin2>

```

Dans les chemins ajouter un fichier `config-name.yaml`

Dans la barre de recherche taper `<config-name>/<profile[/label]`

### Charger un fichier de configuration au démarrage
Dans le fichier de configuration de l'application (_**pas celui sur le serveur**_)
```yaml
spring:
  config:
    import: `[optional:]configserver:<address>`
```

Avec optional l'application ne va pas crasher si le fichier de configuration ne peut pas être chargé depuis le serveur

## Dynamic Updates and High Availability

## Monitoring and versioning
