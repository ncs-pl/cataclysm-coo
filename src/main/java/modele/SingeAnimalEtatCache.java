package modele;

public class SingeAnimalEtatCache extends AnimalEtat {
    private SingeAnimalEtatCache() {
        super(AnimalEtat.ETAT_CACHE);
    }

    /** Obtient l'instance singleton de l'état. */
    public static AnimalEtat obtenirInstance() {
        if (SingeAnimalEtatCache.instance == null) {
            SingeAnimalEtatCache.instance = new SingeAnimalEtatCache();
        }

        return SingeAnimalEtatCache.instance;
    }

    @Override public void deplacer(Animal animal, Jeu jeu) {
        throw new AnimalEtatException("Pas encore implémenté.");
    }

    @Override public void prendreCoup(Animal animal) {
        throw new AnimalEtatException("Pas encore implémenté.");
    }
}
