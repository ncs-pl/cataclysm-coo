package modele;

import vue.Ihm;

/** Un décor sur lequel peut se percher un singe. */
public class Cocotier extends Acteur {
    public static final char SYMBOLE = 'P';
    public static final String AFFICHAGE = Ihm.COULEUR_FOND_NOIR + Ihm.COULEUR_VERT + Cocotier.SYMBOLE + Ihm.COULEUR_REINITIALISATION;

    public Cocotier(int ligne, int colonne, int maxLigne, int maxColonne) {
        super(Acteur.TYPE_COCOTIER, ligne, colonne, maxLigne, maxColonne);
    }

    @Override
    public String toString() {
        return Cocotier.AFFICHAGE;
    }
}
