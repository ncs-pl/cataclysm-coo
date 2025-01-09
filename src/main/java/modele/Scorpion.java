package modele;

import vue.Ihm;

public class Scorpion extends Predateur {
    public static final char SYMBOLE = 'X';
    public static final String AFFICHAGE = Ihm.COULEUR_FOND_ROUGE + Ihm.COULEUR_BLANC + Scorpion.SYMBOLE + Ihm.COULEUR_REINITIALISATION;

    private ScorpionEtat etat = ScorpionEtatMouvement.obtenirInstance();

    public Scorpion(int ligne, int colonne, int maxLigne, int maxColonne) {
        super(Acteur.TYPE_SCORPION, ligne, colonne, maxLigne, maxColonne);
    }

    public Scorpion(int ligne, int colonne, int maxLigne, int maxColonne,int sante) {
        super(Acteur.TYPE_SCORPION, ligne, colonne, maxLigne, maxColonne,sante);
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
        return Scorpion.AFFICHAGE; // TODO(nico): toString sur les Etats ?
    }
}
