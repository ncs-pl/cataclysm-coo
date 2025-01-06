package modele;

import vue.Ihm;

public class ScorpionEtatCache extends ScorpionEtat{
    public static final String AFFICHAGE = Ihm.COULEUR_FOND_ROUGE + Ihm.COULEUR_ROUGE + Scorpion.SYMBOLE + Ihm.COULEUR_REINITIALISATION;

    private static ScorpionEtatCache instance;
    private ScorpionEtatCache(){
        super(ScorpionEtat.ETAT_CACHE);
    }

    public static ScorpionEtatCache obtenirInstance(){
        if(instance == null){
            instance = new ScorpionEtatCache();
        }
        return instance;
    }

    public void deplacer(Scorpion scorpion , Jeu jeu){
        int ligne = scorpion.obtenirLigne();
        int colonne = scorpion.obtenirColonne();

        int stadeRepos = scorpion.obtenirStadeRepos();
        int maxRepos = scorpion.obtenirMaxRepos();
        int maxPaix = scorpion.obtenirMaxPaix();
        int stadePaix = scorpion.obtenirStadePaix();

        Animal proie = jeu.chercherProieScorpionCache(ligne , colonne);

        if(stadeRepos == maxRepos - 1){
            scorpion.changerStadeRepos(0);
            scorpion.changerEtat(ScorpionEtatMouvement.obtenirInstance());
            scorpion.deplacer(jeu);
        }
        else{
            if(stadePaix == maxPaix && proie != null){
                jeu.obtenirAnimaux().remove(proie);
                scorpion.changerStadePaix(0);
            }
            if(stadePaix < maxPaix) scorpion.changerStadePaix(stadePaix + 1);
            scorpion.changerStadeRepos(stadeRepos + 1);
        }
    }

    @Override
    public String toString() {
        return ScorpionEtatCache.AFFICHAGE;
    }
}
