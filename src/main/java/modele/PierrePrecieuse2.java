package modele;

import vue.Ihm;

public class PierrePrecieuse2 extends Objet {
    public static final char SYMBOLE = '2';
    public static final String AFFICHAGE = Ihm.COULEUR_FOND_CYAN + Ihm.COULEUR_VIOLET + PierrePrecieuse2.SYMBOLE + Ihm.COULEUR_REINITIALISATION;

    public PierrePrecieuse2(int ligne, int colonne, int maxLigne, int maxColonne) {
        super(Acteur.TYPE_PIERRE_PRECIEUSE2, ligne, colonne, maxLigne, maxColonne);
    }

    @Override
    public String toString() {
        return PierrePrecieuse2.AFFICHAGE;
    }
}
