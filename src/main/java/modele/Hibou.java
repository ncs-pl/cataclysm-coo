package modele;

import vue.Ihm;

public class Hibou extends Predateur {
    public static final char SYMBOLE = 'H';

    private HibouEtat etat = HibouEtatVol.obtenirInstance();
    public Hibou(int ligne, int colonne, int maxLigne, int maxColonne) {
        super(Acteur.TYPE_HIBOU, ligne, colonne, maxLigne, maxColonne);
    }
    public Hibou(int ligne, int colonne, int maxLigne, int maxColonne,int sante) {
        super(Acteur.TYPE_HIBOU, ligne, colonne, maxLigne, maxColonne,sante);
    }


    public HibouEtat obtenirEtat()               { return etat;      }
    public void      changerEtat(HibouEtat etat) { this.etat = etat; }

    public void deplacer(Jeu jeu) {
        this.etat.deplacer(this, jeu);
    }

    @Override
    public String toString() {
        return this.etat.toString();
    }
}
