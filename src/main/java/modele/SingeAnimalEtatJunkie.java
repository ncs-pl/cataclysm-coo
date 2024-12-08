package modele;

public class SingeAnimalEtatJunkie extends AnimalEtat {
    private SingeAnimalEtatJunkie() {
        super(AnimalEtat.ETAT_JUNKIE);
    }

    /** Obtient l'instance singleton de l'état. */
    public static AnimalEtat obtenirInstance() {
        if (SingeAnimalEtatJunkie.instance == null) SingeAnimalEtatJunkie.instance = new SingeAnimalEtatJunkie();
        return SingeAnimalEtatJunkie.instance;
    }

    @Override public void deplacer(Animal animal, Jeu jeu) {
        throw new AnimalEtatException("Pas encore implémenté.");
    }

    @Override public void prendreCoup(Animal animal) {
        throw new AnimalEtatException("Pas encore implémenté.");
    }
}
