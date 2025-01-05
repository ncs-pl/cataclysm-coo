package modele;

public class SerpentEtatMouvement extends SerpentEtat{
    private static SerpentEtatMouvement instance; // Singleton
    private SerpentEtatMouvement(){
        super(SerpentEtat.ETAT_MOUVEMENT);
    }

    public static SerpentEtatMouvement obtenirInstance(){
        if(SerpentEtatMouvement.instance == null) SerpentEtatMouvement.instance = new SerpentEtatMouvement();
        return SerpentEtatMouvement.instance;
    }

    public void deplacer(Serpent serpent , Jeu jeu){
        // TODO(nico): je suppose TODO car c'était vide...
    }

    @Override
    public String toString() {
        return ""; // TODO(nico): c.f. Serpent.toString()
    }
}
