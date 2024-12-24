package modele;

import vue.Ihm;

/** Un gland pouvant nourrir les écureuils. */
public class Gland extends Objet {
    public Gland(int ligne, int colonne, int maxLigne, int maxColonne) {
        super(Acteur.TYPE_GLAND, ligne, colonne, maxLigne, maxColonne);
    }

    @Override
    public String toString() {
        return Ihm.COULEUR_FOND_ROUGE      +
                Ihm.COULEUR_JAUNE           +
                Acteur.SYMBOLE_GLAND         +
                Ihm.COULEUR_REINITIALISATION;
    }
}
