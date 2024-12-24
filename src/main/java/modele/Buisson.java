package modele;

import vue.Ihm;

/** Un décor où peuvent de cacher les écureuils. */
public class Buisson extends Acteur {
    public Buisson(int ligne, int colonne, int maxLigne, int maxColonne) {
        super(Acteur.TYPE_BUISSON, ligne, colonne, maxLigne, maxColonne);
    }

    @Override
    public String toString() {
        return Ihm.COULEUR_FOND_NOIR       +
                Ihm.COULEUR_VERT            +
                Acteur.SYMBOLE_BUISSON       +
                Ihm.COULEUR_REINITIALISATION;
    }
}
