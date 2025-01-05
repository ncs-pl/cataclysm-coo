package modele;

import vue.Ihm;

/** Une cellule vide dans la carte. */
public class ZoneVide extends Acteur {
    public static final char SYMBOLE = '.';

    public ZoneVide(int ligne, int colonne, int maxLigne, int maxColonne) {
        super(Acteur.TYPE_ZONE_VIDE, ligne, colonne, maxLigne, maxColonne);
    }

    @Override
    public String toString() {
        return Ihm.COULEUR_FOND_VERT + ZoneVide.SYMBOLE + Ihm.COULEUR_REINITIALISATION;
    }
}
