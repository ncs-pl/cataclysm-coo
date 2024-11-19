package modele;

import java.util.List;

public class Jeu {
    private List<List<Acteur>> carte;
    private Personnage personnage;
    private List<Objet> objets;
    private List<Animal> animaux;
    private int tourCourant;

    public List<List<Acteur>> getCarte() {
        return carte;
    }

    public void setCarte(List<List<Acteur>> carte) {
        this.carte = carte;
    }

    public Jeu() {
        // TODO: faire le jeu
    }
}
