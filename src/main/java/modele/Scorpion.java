package modele;

import vue.Ihm;

//TODO(Younes) : Décorateur pour l'état pacifique
public class Scorpion extends Predateur {
    public static final char SYMBOLE = 'X';

    private ScorpionEtat etat = ScorpionEtatMouvement.obtenirInstance();
    private final int maxRepos;
    private final int maxPaix;
    private int stadeRepos;
    private int stadePaix;

    public Scorpion(int ligne, int colonne, int maxLigne, int maxColonne) {
        super(Acteur.TYPE_SCORPION, ligne, colonne, maxLigne, maxColonne);
        this.maxRepos = 5;
        this.stadeRepos = 0;
        this.maxPaix = 2;
        this.stadePaix = 2;
    }

    public int obtenirMaxPaix() {
        return maxPaix;
    }

    public int obtenirStadePaix() {
        return stadePaix;
    }

    public void changerStadePaix(int stadePaix) {
        this.stadePaix = stadePaix;
    }

    public int obtenirMaxRepos() {
        return maxRepos;
    }

    public int obtenirStadeRepos() {
        return stadeRepos;
    }

    public void changerStadeRepos(int stadeRepos) {
        this.stadeRepos = stadeRepos;
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
    public String toString() {return etat.toString();}
}
