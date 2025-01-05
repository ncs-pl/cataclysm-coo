package modele;

import java.util.List;
import java.util.Random;

public class Hibou extends Predateur{
    private HibouEtat etat = HibouEtatVol.obtenirInstance();
    public Hibou(int ligne, int colonne, int maxLigne, int maxColonne) {
        super(Acteur.TYPE_HIBOU, ligne, colonne, maxLigne, maxColonne);
    }

    public HibouEtat obtenirEtat() {
        return etat;
    }

    public void changerEtat(HibouEtat etat) {
        this.etat = etat;
    }

    public void deplacer(Jeu jeu) {
        etat.deplacer(this,jeu);
    }

    @Override public String toString() { return ""; } // TODO
}
