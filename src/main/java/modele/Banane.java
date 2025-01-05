package modele;

import vue.Ihm;

/** Une banane pour nourrir un singe. */
public class Banane extends Objet {
    public static final char SYMBOLE = 'N';
    public static final String AFFICHAGE = Ihm.COULEUR_FOND_JAUNE + Ihm.COULEUR_NOIR + Banane.SYMBOLE + Ihm.COULEUR_REINITIALISATION;

    public Banane(int ligne, int colonne, int maxLigne, int maxColonne) {
      super(Acteur.TYPE_BANANE, ligne, colonne, maxLigne, maxColonne);
    }

    @Override
    public String toString() {
        return Banane.AFFICHAGE;
    }
}
