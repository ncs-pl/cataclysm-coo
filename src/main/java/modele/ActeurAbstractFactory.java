package modele;

public interface ActeurAbstractFactory {
    Animal creerAnimal(int ligne, int colonne, int maxLignes, int maxColonnes);                     // Créer un acteur animal.
    Acteur creerDecor1(int ligne, int colonne, int maxLignes, int maxColonnes);                     // Créer un acteur décoratif.
    Acteur creerDecor2(int ligne, int colonne, int maxLignes, int maxColonnes);                     // Créer un acteur décoratif.
    Objet creerObjetAliment(int ligne, int colonne, int maxLignes, int maxColonnes);                // Créer un acteur consommable
    Objet creerObjetChampignon(int ligne, int colonne, int maxLignes, int maxColonnes);             // Créer un acteur champignon
    Objet creerObjetChampignonDrogue(int ligne, int colonne, int maxLignes, int maxColonnes);       // Créer un acteur champignon de la drogue
    Predateur creerPredateur1(int ligne, int colonne, int maxLignes, int maxColonnes);              // Créer le premier type de prédateur.
    Predateur creerPredateur2(int ligne, int colonne, int maxLignes, int maxColonnes);              // Créer le second type de prédateur.
    Personnage creerPersonnage(int ligne, int colonne, int maxLignes, int maxColonnes);             // Créer le personnage.
    ZoneVide creerZoneVide(int ligne, int colonne, int maxLignes, int maxColonnes);                 // Créer une zone vide.
    PierrePrecieuse2 creerPierrePrecieuse2(int ligne, int colonne, int maxLignes, int maxColonnes); // Créer une pierre précieuse de puissance 2.
    PierrePrecieuse3 creerPierrePrecieuse3(int ligne, int colonne, int maxLignes, int maxColonnes); // Créer une pierre précieuse de puissance 3.
    SimpleCaillou creerSimpleCaillou(int ligne, int colonne, int maxLignes, int maxColonnes);       // Créer un simple caillou.
    Acteur creerParSymbole(char symbole, int ligne, int colonne, int maxLignes, int maxColonnes);   // Créer l'acteur selon le symbole Carte lu.
}
