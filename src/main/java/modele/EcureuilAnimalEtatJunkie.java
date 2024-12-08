package modele;

public class EcureuilAnimalEtatJunkie extends AnimalEtat {
    private EcureuilAnimalEtatJunkie() {
        super(AnimalEtat.ETAT_JUNKIE);
    }

    /** Obtient l'instance singleton de l'état. */
    public static AnimalEtat obtenirInstance() {
        if (EcureuilAnimalEtatJunkie.instance == null) {
            EcureuilAnimalEtatJunkie.instance = new EcureuilAnimalEtatJunkie();
        }

        return EcureuilAnimalEtatJunkie.instance;
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
