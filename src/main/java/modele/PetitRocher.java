package modele;

import vue.Ihm;

/** Un décor où peuvent se cacher les singes. */
public class PetitRocher extends Acteur {
    public static final char SYMBOLE = 'O';

    public PetitRocher(int ligne, int colonne, int maxLigne, int maxColonne) {
        super(Acteur.TYPE_PETIT_ROCHER, ligne, colonne, maxLigne, maxColonne);
    }

    @Override
    public String toString() {
        return Ihm.COULEUR_FOND_NOIR + Ihm.COULEUR_BLANC + PetitRocher.SYMBOLE + Ihm.COULEUR_REINITIALISATION;
    }
}
