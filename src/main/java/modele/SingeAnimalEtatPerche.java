package modele;

public class SingeAnimalEtatPerche extends AnimalEtat {
    private SingeAnimalEtatPerche() {
        super(AnimalEtat.ETAT_PERCHE);
    }

    /** Obtient l'instance singleton de l'état. */
    public static AnimalEtat obtenirInstance() {
        if (SingeAnimalEtatPerche.instance == null) {
            SingeAnimalEtatPerche.instance = new SingeAnimalEtatPerche();
        }

        return SingeAnimalEtatPerche.instance;
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
