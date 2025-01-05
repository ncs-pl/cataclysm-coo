package modele;

import vue.Ihm;

/** Un décor où peuvent de cacher les écureuils. */
public class Buisson extends Acteur {
    public static final char SYMBOLE = 'B';

    public Buisson(int ligne, int colonne, int maxLigne, int maxColonne) {
        super(Acteur.TYPE_BUISSON, ligne, colonne, maxLigne, maxColonne);
    }

    @Override
    public String toString() {
        return Ihm.COULEUR_FOND_NOIR + Ihm.COULEUR_VERT + Buisson.SYMBOLE + Ihm.COULEUR_REINITIALISATION;
    }
}
