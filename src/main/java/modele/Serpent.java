package modele;

import vue.Ihm;

public class Serpent extends Predateur {
    public static final char SYMBOLE = 'Z';

    private SerpentEtat etat = SerpentEtatMouvement.obtenirInstance();
    private int stadeRepos = 0;

    public Serpent(int ligne, int colonne, int maxLigne, int maxColonne) {
        super(Acteur.TYPE_SERPENT, ligne, colonne, maxLigne, maxColonne);
    }

    // NOTE(younes): pour la factory
    public Serpent(int ligne, int colonne, int maxLigne, int maxColonne,int sante) {
        super(Acteur.TYPE_SERPENT, ligne, colonne, maxLigne, maxColonne,sante);
    }

    public int obtenirStadeRepos() {
        return stadeRepos;
    }

    public void changerStadeRepos(int stadeRepos) {
        this.stadeRepos = stadeRepos;
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
        return etat.toString();
    }
}
