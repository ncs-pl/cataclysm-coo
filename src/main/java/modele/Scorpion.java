package modele;

import vue.Ihm;

public class Scorpion extends Predateur {
    public static final char SYMBOLE = 'X';

    private ScorpionEtat etat = ScorpionEtatMouvement.obtenirInstance();

    public Scorpion(int ligne, int colonne, int maxLigne, int maxColonne) {
        super(Acteur.TYPE_SCORPION, ligne, colonne, maxLigne, maxColonne);
    }

    public ScorpionEtat obtenirEtat() {
        return etat;
    }

    public void changerEtat(ScorpionEtat etat) {
        this.etat = etat;
    }

    @Override
    public void deplacer(Jeu jeu) {
        etat.deplacer(this,jeu);
    }

    @Override
    public String toString() {
        return Ihm.COULEUR_FOND_ROUGE + Ihm.COULEUR_BLANC + Scorpion.SYMBOLE + Ihm.COULEUR_REINITIALISATION; // TODO(nico): toString sur les Etats ?
    }
}
