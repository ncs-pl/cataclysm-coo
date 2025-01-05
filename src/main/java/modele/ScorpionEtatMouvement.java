package modele;

public class ScorpionEtatMouvement extends ScorpionEtat{

    private static ScorpionEtatMouvement instance;
    private ScorpionEtatMouvement(){
        super(SerpentEtat.ETAT_MOUVEMENT);
    }

    public static ScorpionEtatMouvement obtenirInstance(){
        if(instance == null){
            instance = new ScorpionEtatMouvement();
        }
        return instance;
    }

    public void deplacer(Scorpion scorpion , Jeu jeu){

    }
}
