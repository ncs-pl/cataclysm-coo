package modele;

import vue.Ihm;

/** Un décor où peuvent se percher des écureuils. */
public class Arbre extends Acteur {
    public static final char SYMBOLE = 'A';

    public Arbre(int ligne, int colonne, int maxLigne, int maxColonne) {
        super(Acteur.TYPE_ARBRE, ligne, colonne, maxLigne, maxColonne);
    }

    @Override
    public String toString() {
        return Ihm.COULEUR_FOND_NOIR + Ihm.COULEUR_VERT + Arbre.SYMBOLE + Ihm.COULEUR_REINITIALISATION;
    }
}