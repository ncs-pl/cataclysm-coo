package modele;

public class EcureuilAnimalEtatAffame extends AnimalEtat {
    private EcureuilAnimalEtatAffame() {
        super(AnimalEtat.ETAT_AFFAME);
    }

    /** Obtient l'instance singleton de l'état. */
    public static AnimalEtat obtenirInstance() {
        if (EcureuilAnimalEtatAffame.instance == null) {
            EcureuilAnimalEtatAffame.instance = new EcureuilAnimalEtatAffame();
        }

        return EcureuilAnimalEtatAffame.instance;
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
