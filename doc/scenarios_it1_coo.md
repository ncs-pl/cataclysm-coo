## Cas d'utilisations


### "Jouer jeu de survie"

**Périmètre :** Système de jeu

**Niveau :** But utilisateur

**Acteur principal :** Le joueur

**Parties prenantes et intérêts :** Le joueur

**Pré-conditions :** Aucune

**Post-conditions :** Le joueur a joué une partie du jeu de survie.

**Scénario nominal :**

1. Le système demande aux joueur de saisir l'emplacement de sa carte ou créer une nouvelle carte.
2. Le joueur fait son choix.
3. Le système démarre une partie.
4. Le système affiche l’état de la partie.
5. Le système invite le joueur à jouer entrer une instruction.
6. Le joueur saisit son instruction.
7. Le système execute l'instruction.
8. Le système execute les actions des animaux.



**Extensions :**


- *2.a* Le joueur choisit de créer une nouvelle carte.
    1. Le système demande de saisir le nombre de lignes , ensuite le nombre de colonnes de la carte.
    2. Le joueur saisit les dimensions souhaitées.
    3. Le système demande de choisir un thème de jeu parmi ceux disponibles.
    4. Le joueur choisit le thème souhaité.
    5. Le système génère la carte.

- *6.a* Le joueur choisit d'arrêter le jeu.
    1. Le système affiche l'état de la partie.
    2. Le système arrête le jeu.

- *6.b* Le joueur choisit de faire un déplacement.
    1. Le système vérifie que le déplacement est possible.
        - *1.a* Le déplacement n'est pas possible (Bordure de carte / Case occupée etc).
            1. Le système affiche un message d'avertissement.
            2. Retour au point 5 du scénario nominal.  
- *6.c* Le joueur choisit de ramasser un objet.
    1. Le système vérifie la présence d'un objet adjascent au joueur.
        - *1.a* Aucun objet à côté du joueur.
            1. Le système affiche un message d'avertissement.
            2. Retour au point 5 du scénario nominal.  
    2. Le système rajoute l'objet à l'inventaire du joueur et le supprime de la carte.

- *6.d* Le joueur choisit de déposer un objet.
    1. Le système vérifie le contenu de l'inventaire.
        - *1.a* L'inventaire est vide.
            1. Le système affiche un message d'avertissement.
            2. Retour au point 5 du scénario nominal.  
    2. Le système demande de saisir le numéro de l'objet à déposer.
        - *2.a* Le numéro ne correspond à aucun objet.
            1. Le système affiche un message d'avertissement.
            2. Retour au point 5 du scénario nominal.  
    3. Le système vérifie que la case où l'objet sera déposé est libre.
        - *3.a* La case est occupée.
            1. Le système affiche un message d'avertissement.
            2. Retour au point 5 du scénario nominal. 
    4. Le système retire l'objet de l'inventaire et le rajoute à la carte à la position souhaitée.
        - *4.a* L'objet est déposé sur une case adjascente à un animal.
            1. L'animal se nourrit.
            2. L'animal devient ami du joueur.

            
