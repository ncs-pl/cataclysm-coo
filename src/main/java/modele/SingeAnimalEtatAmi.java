package modele;

public class SingeAnimalEtatAmi extends AnimalEtat {

    private static SingeAnimalEtatAmi instance;

    private SingeAnimalEtatAmi() {
        super(AnimalEtat.ETAT_AMI);
    }

    /** Obtient l'instance singleton de l'état. */
    public static AnimalEtat obtenirInstance() {
        if (SingeAnimalEtatAmi.instance == null) SingeAnimalEtatAmi.instance = new SingeAnimalEtatAmi();
        return SingeAnimalEtatAmi.instance;
    }

    @Override public void deplacer(Animal animal, Jeu jeu) {
        throw new AnimalEtatException("Comportement non-spécifié.");
    }

    @Override public void prendreCoup(Animal animal) {
        animal.changerEtat(SingeAnimalEtatRassasie.obtenirInstance());
    }

    @Override
    public String toString() {
        return "";
    }
}
