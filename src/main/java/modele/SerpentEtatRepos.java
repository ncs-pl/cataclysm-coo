package modele;

public class SerpentEtatRepos extends SerpentEtat{

    private static SerpentEtatRepos instance;
    private SerpentEtatRepos(){
        super(SerpentEtat.ETAT_REPOS);
    }

    public static SerpentEtatRepos obtenirInstance(){
        if(instance == null){
            instance = new SerpentEtatRepos();
        }
        return instance;
    }

    public void deplacer(Serpent serpent , Jeu jeu){
        serpent.changerEtat(SerpentEtatMouvement.obtenirInstance());
    }
}
