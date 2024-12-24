package modele;

import vue.Ihm;

/** Un décor sur lequel peut se percher un singe. */
public class Cocotier extends Acteur {
    public Cocotier(int ligne, int colonne, int maxLigne, int maxColonne) {
        super(Acteur.TYPE_COCOTIER, ligne, colonne, maxLigne, maxColonne);
    }

    @Override
    public String toString() {
        return Ihm.COULEUR_FOND_NOIR       +
                Ihm.COULEUR_VERT            +
                Acteur.SYMBOLE_COCOTIER      +
                Ihm.COULEUR_REINITIALISATION;
    }
}
