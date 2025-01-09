package modele;

import vue.Ihm;

public class PierrePrecieuse3 extends Objet {
    public static final char SYMBOLE = '3';
    public static final String AFFICHAGE = Ihm.COULEUR_FOND_CYAN + Ihm.COULEUR_VIOLET + PierrePrecieuse3.SYMBOLE + Ihm.COULEUR_REINITIALISATION;

    public PierrePrecieuse3(int ligne, int colonne, int maxLigne, int maxColonne) {
        super(Acteur.TYPE_PIERRE_PRECIEUSE3, ligne, colonne, maxLigne, maxColonne);
    }

    @Override
    public String toString() {
        return PierrePrecieuse3.AFFICHAGE;
    }
}
