# Yacine BENICHOU EPF rentmanager webApp

## Initialiser la base de données

```
Run src\main\java\com\epf\rentmanager\persistence\FillDatabase.java
```

### Initialiser la webApp
Lancer le serveur tomcat7 :
```
mvn tomcat7:run
```
Ouvrir le lien :
```
http://localhost:8080/rentmanager
```

Lancer l'ensemble des tests unitaires (Attention à bien run fillDatabase.java à chaque fois que vous souhaitez lancer la commande ci-dessous, sinon des erreurs peuvent apparaître !): 
```
mvn test
```
### Exercice Atteint

Fin du TP 3.

### Fonctionnalités implémentées
* Maven et spring fonctionnels.
* Create vehicule/client/reservation.
* Update vehicule/client/reservation.
* Delete vehicule/client/reservation.
* Details vehicule/client (pas de détails pertinents à afficher pour les réservations).
* Contraintes Front-End et Back-End (Package Validator).
* Redirection vers erreur en question sur le Front-end et Exception customisée et adaptée avec les informations nécessaires sur le serveur.
* Tests Unitaires pour les 3 services testant chaque méthode implémentée.

### Problèmes rencontrés

* Pas de Mock test de fait car difficulté à implémenter la librairie "Mockito".
* Difficultés à réaliser des tests unitaires qui soient indépendants les uns des autres.
* Problème de version avec JUnit. Nécessité de passer à la version 4.12 pour être utilisé en parallèle avec spring-test.
* Une erreur est apparue sur les fichiers header.js et footer.js sur le chemin src/main/java/webapp/resources/bower_components/jquery-sparkline/src.
