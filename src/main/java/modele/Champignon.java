package modele;

import vue.Ihm;

/** Un objet pouvant nourrir des animaux pour les rendre junkies. */
public class Champignon extends Objet {
    public Champignon(int ligne, int colonne, int maxLigne, int maxColonne) {
        super(Acteur.TYPE_CHAMPIGNON, ligne, colonne, maxLigne, maxColonne);
    }

    @Override
    public String toString() {
        return Ihm.COULEUR_FOND_BLANC      +
                Ihm.COULEUR_ROUGE           +
                Acteur.SYMBOLE_CHAMPIGNON    +
                Ihm.COULEUR_REINITIALISATION;
    }
}
