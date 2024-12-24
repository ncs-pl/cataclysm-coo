package modele;

import vue.Ihm;

/** Une banane pour nourrir un singe. */
public class Banane extends Objet {
    public Banane(int ligne, int colonne, int maxLigne, int maxColonne) {
      super(Acteur.TYPE_BANANE, ligne, colonne, maxLigne, maxColonne);
    }

    @Override
    public String toString() {
        return Ihm.COULEUR_FOND_JAUNE      +
                Ihm.COULEUR_NOIR            +
                Acteur.SYMBOLE_BANANE        +
                Ihm.COULEUR_REINITIALISATION;
    }
}
