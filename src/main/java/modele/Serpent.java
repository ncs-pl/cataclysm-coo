package modele;

import vue.Ihm;

public class Serpent extends Predateur {
    public static final char SYMBOLE = 'Z';

    private SerpentEtat etat = SerpentEtatMouvement.obtenirInstance();

    public Serpent(int ligne, int colonne, int maxLigne, int maxColonne) {
        super(Acteur.TYPE_SERPENT, ligne, colonne, maxLigne, maxColonne);
    }

    public SerpentEtat obtenirEtat() {
        return etat;
    }

    public void changerEtat(SerpentEtat etat) {
        this.etat = etat;
    }

    @Override
    public void deplacer(Jeu jeu) {
        etat.deplacer(this,jeu);
    }

    @Override
    public String toString() {
        return Ihm.COULEUR_FOND_BLANC + Ihm.COULEUR_NOIR + Serpent.SYMBOLE + Ihm.COULEUR_REINITIALISATION; // TODO(nico): toString sur les Etats ?
    }
}
