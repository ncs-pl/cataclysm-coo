package modele;

/** Un animal affamé. */
public class AnimalEtatAffame extends AnimalEtat {
    AnimalEtatAffame(Animal animal) {
        super(AnimalEtat.ETAT_AFFAME, animal);
    }
}
