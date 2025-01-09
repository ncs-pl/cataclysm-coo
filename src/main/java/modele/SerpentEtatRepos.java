package modele;

import vue.Ihm;

public class SerpentEtatRepos extends SerpentEtat {
    public static final String AFFICHAGE = Ihm.COULEUR_FOND_BLANC + Ihm.COULEUR_ROUGE + Serpent.SYMBOLE + Ihm.COULEUR_REINITIALISATION;
    private static SerpentEtatRepos instance; // Singleton
    private SerpentEtatRepos() {
        super(SerpentEtat.ETAT_REPOS);
    }

    public static SerpentEtatRepos obtenirInstance(){
        if(SerpentEtatRepos.instance == null) SerpentEtatRepos.instance = new SerpentEtatRepos();
        return SerpentEtatRepos.instance;
    }

    public void deplacer(Serpent serpent , Jeu jeu){
        int stade = serpent.obtenirStadeRepos();
        if(stade == 2){
            serpent.changerStadeRepos(0);
            serpent.changerEtat(SerpentEtatMouvement.obtenirInstance());
        }
        else{
            serpent.changerStadeRepos(stade+1);
        }
    }

    @Override
    public String toString() {
        return SerpentEtatRepos.AFFICHAGE;
    }
}
