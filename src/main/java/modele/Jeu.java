package modele;

import java.util.List;

public class Jeu {
    private List<List<ElementCarte>> carte;
    private Personnage personnage;
    private List<Objet> objets;
    private List<Animal> animaux;
    private int tourCourant;

    public List<List<ElementCarte>> getCarte() {
        return carte;
    }

    public void setCarte(List<List<ElementCarte>> carte) {
        this.carte = carte;
    }

    public Personnage getPersonnage() {
        return personnage;
    }

    public List<Objet> getObjets() {
        return objets;
    }

    public void setObjets(List<Objet> objets) {
        this.objets = objets;
    }

    public List<Animal> getAnimaux() {
        return animaux;
    }

    public void setAnimaux(List<Animal> animaux) {
        this.animaux = animaux;
    }

    public int getTourCourant() {
        return tourCourant;
    }

    public void setTourCourant(int tourCourant) {
        this.tourCourant = tourCourant;
    }

    public Jeu() {
        // TODO: faire le jeu
    }
}
