package modele;

import vue.Ihm;

public class ChampignonVeneneux extends Objet {
    public static final char SYMBOLE = 'M';

    public ChampignonVeneneux(int ligne, int colonne, int maxLigne, int maxColonne) {
        super(Acteur.TYPE_CHAMPIGNON_VENENEUX, ligne, colonne, maxLigne, maxColonne);
    }

    @Override
    public String toString() {
        return Ihm.COULEUR_FOND_BLANC + Ihm.COULEUR_JAUNE + ChampignonVeneneux.SYMBOLE + Ihm.COULEUR_REINITIALISATION;
    }
}
