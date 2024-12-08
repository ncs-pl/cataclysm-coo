package modele;

public class EcureuilAnimalEtatPerche extends AnimalEtat {
    private EcureuilAnimalEtatPerche() {
        super(AnimalEtat.ETAT_PERCHE);
    }

    /** Obtient l'instance singleton de l'état. */
    public static AnimalEtat obtenirInstance() {
        if (EcureuilAnimalEtatPerche.instance == null) {
            EcureuilAnimalEtatPerche.instance = new EcureuilAnimalEtatPerche();
        }

        return EcureuilAnimalEtatPerche.instance;
    }

    @Override public void deplacer(Animal animal, Jeu jeu) {
        throw new AnimalEtatException("Pas encore implémenté.");
    }

    @Override public void manger(Animal animal, Jeu jeu) {
        throw new AnimalEtatException("Pas encore implémenté.");
    }

    @Override public void prendreCoup(Animal animal) {
        throw new AnimalEtatException("Pas encore implémenté.");
    }
}
