package modele;

public class ScorpionEtatCache extends ScorpionEtat{

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
        scorpion.changerEtat(ScorpionEtatMouvement.obtenirInstance());
    }
}
