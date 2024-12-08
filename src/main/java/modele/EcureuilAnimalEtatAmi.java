package modele;

public class EcureuilAnimalEtatAmi extends AnimalEtat {
    private EcureuilAnimalEtatAmi() {
        super(AnimalEtat.ETAT_AMI);
    }

    /** Obtient l'instance singleton de l'état. */
    public static AnimalEtat obtenirInstance() {
        if (EcureuilAnimalEtatAmi.instance == null) {
            EcureuilAnimalEtatAmi.instance = new EcureuilAnimalEtatAmi();
        }

        return EcureuilAnimalEtatAmi.instance;
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
