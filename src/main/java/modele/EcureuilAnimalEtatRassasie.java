package modele;

public class EcureuilAnimalEtatRassasie extends AnimalEtat {
    private EcureuilAnimalEtatRassasie() {
        super(AnimalEtat.ETAT_RASSASIE);
    }

    /** Obtient l'instance singleton de l'état. */
    public static AnimalEtat obtenirInstance() {
        if (EcureuilAnimalEtatRassasie.instance == null) {
            EcureuilAnimalEtatRassasie.instance = new EcureuilAnimalEtatRassasie();
        }

        return EcureuilAnimalEtatRassasie.instance;
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
