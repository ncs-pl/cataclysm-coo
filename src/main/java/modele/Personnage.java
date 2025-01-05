package modele;

import vue.Ihm;

/** Le joueur sur la carte. */
public class Personnage extends Acteur {
    public static final char SYMBOLE = '@';

    private int sante; // La santé du joueur, entre 0 et 100.

    public Personnage(int ligne, int colonne, int maxLigne, int maxColonne) {
        super(Acteur.TYPE_PERSONNAGE, ligne, colonne, maxLigne, maxColonne);
        this.sante = 100;
    }

    /** Obtient la santé du joueur. */
    public int obtenirSante() {
        return this.sante;
    }

    /** Change la santé du joueur. */
    public void changerSante(int sante) {
        assert(sante >= 0);
        assert(sante <= 100);
        this.sante = sante;
    }

    @Override
    public String toString() {
        return Ihm.COULEUR_FOND_BLANC + Ihm.COULEUR_VIOLET + Personnage.SYMBOLE + Ihm.COULEUR_REINITIALISATION;
    }
}
