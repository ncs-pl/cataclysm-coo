package modele;

import java.util.List;

public class Jeu {
    List<List<ElementCarte>> carte;
    Personnage personnage;
    List<Objet> objets;
    List<Animal> animaux;
    int tourCourant;

    public String afficherCarte() {
        String affichage = "";
        for (List<ElementCarte> ligne : carte) {
            for (ElementCarte element : ligne) {
                affichage += element.getSymbole();
            }
            affichage += '\n';
        }
        return affichage;
    }

    public Jeu() {
        // TODO: faire le jeu
    }
}
