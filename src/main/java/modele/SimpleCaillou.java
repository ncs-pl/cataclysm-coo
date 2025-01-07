package modele;

import vue.Ihm;

public class SimpleCaillou extends Objet {
    public static final char SYMBOLE = '0';
    public static final String AFFICHAGE = Ihm.COULEUR_FOND_CYAN + Ihm.COULEUR_VIOLET + SimpleCaillou.SYMBOLE + Ihm.COULEUR_REINITIALISATION;

    public SimpleCaillou(int ligne, int colonne, int maxLigne, int maxColonne) {
        super(Acteur.TYPE_SIMPLE_CAILLOU, ligne, colonne, maxLigne, maxColonne);
    }

    @Override
    public String toString() {
        return SimpleCaillou.AFFICHAGE;
    }
}
