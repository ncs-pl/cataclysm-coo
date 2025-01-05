package modele;

public class SerpentEtatMouvement extends SerpentEtat{

    private static SerpentEtatMouvement instance;
    private SerpentEtatMouvement(){
        super(SerpentEtat.ETAT_MOUVEMENT);
    }

    public static SerpentEtatMouvement obtenirInstance(){
        if(instance == null){
            instance = new SerpentEtatMouvement();
        }
        return instance;
    }

    public void deplacer(Serpent serpent , Jeu jeu){

    }
}
