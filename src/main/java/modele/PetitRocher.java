package modele;

import vue.Ihm;

/** Un décor où peuvent se cacher les singes. */
public class PetitRocher extends Acteur {
    public static final char SYMBOLE = 'O';
    public static final String AFFICHAGE = Ihm.COULEUR_FOND_NOIR + Ihm.COULEUR_BLANC + PetitRocher.SYMBOLE + Ihm.COULEUR_REINITIALISATION;

    public PetitRocher(int ligne, int colonne, int maxLigne, int maxColonne) {
        super(Acteur.TYPE_PETIT_ROCHER, ligne, colonne, maxLigne, maxColonne);
    }

    @Override
    public String toString() {
        return PetitRocher.AFFICHAGE;
    }
}
