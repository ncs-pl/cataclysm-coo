package modele;

public class ScorpionEtatMouvement extends ScorpionEtat {
    private static ScorpionEtatMouvement instance; // Singleton
    private ScorpionEtatMouvement() {
        super(SerpentEtat.ETAT_MOUVEMENT);
    }

    public static ScorpionEtatMouvement obtenirInstance() {
        if(ScorpionEtatMouvement.instance == null) ScorpionEtatMouvement.instance = new ScorpionEtatMouvement();
        return ScorpionEtatMouvement.instance;
    }

    public void deplacer(Scorpion scorpion , Jeu jeu) {
        // TODO(nico): je suppose TODO car y avait rien ici
    }

    @Override
    public String toString() {
        return ""; // TODO(nico): c.f. Scorpion.toString()
    }
}
