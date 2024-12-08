package modele;

public class SingeAnimalEtatRassasie extends AnimalEtat {
    private SingeAnimalEtatRassasie() {
        super(AnimalEtat.ETAT_RASSASIE);
    }

    /** Obtient l'instance singleton de l'état. */
    public static AnimalEtat obtenirInstance() {
        if (SingeAnimalEtatRassasie.instance == null) {
            SingeAnimalEtatRassasie.instance = new SingeAnimalEtatRassasie();
        }

        return SingeAnimalEtatRassasie.instance;
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
