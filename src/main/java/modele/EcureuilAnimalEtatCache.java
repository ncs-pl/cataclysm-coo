package modele;

public class EcureuilAnimalEtatCache extends AnimalEtat {
    private EcureuilAnimalEtatCache() {
        super(AnimalEtat.ETAT_CACHE);
    }

    /** Obtient l'instance singleton de l'état. */
    public static AnimalEtat obtenirInstance() {
        if (EcureuilAnimalEtatCache.instance == null) {
            EcureuilAnimalEtatCache.instance = new EcureuilAnimalEtatCache();
        }

        return EcureuilAnimalEtatCache.instance;
    }

    @Override public void deplacer(Animal animal) {
        // TODO(nico)
    }

    @Override public void manger(Animal animal) {
        // TODO(nico)
    }

    @Override public void prendreCoup(Animal animal) {
        // TODO(nico)
    }
}
