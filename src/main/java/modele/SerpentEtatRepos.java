package modele;

public class SerpentEtatRepos extends SerpentEtat {
    private static SerpentEtatRepos instance; // Singleton
    private SerpentEtatRepos() {
        super(SerpentEtat.ETAT_REPOS);
    }

    public static SerpentEtatRepos obtenirInstance(){
        if(SerpentEtatRepos.instance == null) SerpentEtatRepos.instance = new SerpentEtatRepos();
        return SerpentEtatRepos.instance;
    }

    public void deplacer(Serpent serpent , Jeu jeu){
        serpent.changerEtat(SerpentEtatMouvement.obtenirInstance());
    }

    @Override
    public String toString() {
        return ""; // TODO(nico): c.f. Serpent.toString()
    }
}
