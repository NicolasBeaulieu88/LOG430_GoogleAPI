# LOG430-Eq07


## Comment Builder & Runner l'app avec Docker

### Prérequis

- Avoir [Docker](https://www.docker.com/products/docker-desktop) d'installé
- Avoir bien configurer son JAVA_HOME
-- Trouver son jdk (normalement dans "C:\Program Files\Java")
  -- Dans Powershell, executer la commande
  ```setx -m JAVA_HOME "C:\Progra~1\Java\jdk1.8.0_XX"```
  -- Redemarrer l'ordinateur
- Avoir pull la dernière version de [OpenJdk](https://hub.docker.com/_/openjdk) dans Docker
  -- Dans Powershell, executer la commande
  ```docker pull openjdk```

### 1ère étape: Builder le projet en .jar
- À partir de la racine du projet, lancer la commande
  ```./mvnw package```

### 2e étape: Creer l'image Docker
- À partir de la racine du projet, lancer la commande
  ```docker build -t springio/eq07-rcs1 .```

### 3e étape: Lancer un container avec l'image créée
- Dans Powershell, executer la commande
  ```docker run -p 8080:8080 springio/eq07-rcs1```