package modele;

import vue.Ihm;

/** Un objet pouvant nourrir des animaux pour les rendre junkies. */
public class Champignon extends Objet {
    public static final char SYMBOLE = 'C';
    public static final String AFFICHAGE = Ihm.COULEUR_FOND_BLANC + Ihm.COULEUR_ROUGE + Champignon.SYMBOLE + Ihm.COULEUR_REINITIALISATION;

    public Champignon(int ligne, int colonne, int maxLigne, int maxColonne) {
        super(Acteur.TYPE_CHAMPIGNON, ligne, colonne, maxLigne, maxColonne);
    }

    @Override
    public String toString() {
        return Champignon.AFFICHAGE;
    }
}
