package modele;

public class EcureuilAnimalEtatCache extends AnimalEtat {

    private static EcureuilAnimalEtatCache instance;

    private EcureuilAnimalEtatCache() {
        super(AnimalEtat.ETAT_CACHE);
    }

    /** Obtient l'instance singleton de l'état. */
    public static AnimalEtat obtenirInstance() {
        if (EcureuilAnimalEtatCache.instance == null) EcureuilAnimalEtatCache.instance = new EcureuilAnimalEtatCache();
        return EcureuilAnimalEtatCache.instance;
    }

    @Override public void deplacer(Animal animal, Jeu jeu) {
        throw new AnimalEtatException("Pas encore implémenté.");
    }

    @Override public void prendreCoup(Animal animal) {
        throw new AnimalEtatException("Pas encore implémenté.");
    }
    
    @Override
    public String toString() {
        return "";
    }
}
