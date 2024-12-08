package modele;

public class SingeAnimalEtatAmi extends AnimalEtat {
    private SingeAnimalEtatAmi() {
        super(AnimalEtat.ETAT_AMI);
    }

    /** Obtient l'instance singleton de l'état. */
    public static AnimalEtat obtenirInstance() {
        if (SingeAnimalEtatAmi.instance == null) {
            SingeAnimalEtatAmi.instance = new SingeAnimalEtatAmi();
        }

        return SingeAnimalEtatAmi.instance;
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
