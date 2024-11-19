package modele;

import java.util.List;

public class Jeu {
    private final JeuTheme theme;
    private final List<List<Acteur>> carte;

    private Personnage personnage;
    private List<Objet> objets;
    private List<Animal> animaux;
    private int tourCourant;

    public Jeu(JeuTheme theme, List<List<Acteur>> carte) {
        this.theme = theme;
        this.carte = carte;
        // TODO: faire le jeu
    }

    public JeuTheme getTheme() {
        return theme;
    }

    public List<List<Acteur>> getCarte() {
        return carte;
    }
}
