package modele;

import vue.Ihm;

public class SingeAnimalEtatAmi extends AnimalEtat {
    public static final String AFFICHAGE = Ihm.COULEUR_FOND_JAUNE + Ihm.COULEUR_VIOLET + Singe.SYMBOLE + Ihm.COULEUR_REINITIALISATION;

    private static SingeAnimalEtatAmi instance; // Singleton
    private SingeAnimalEtatAmi() {
        super(AnimalEtat.ETAT_AMI);
    }

    /** Obtient l'instance singleton de l'état. */
    public static AnimalEtat obtenirInstance() {
        if (SingeAnimalEtatAmi.instance == null) SingeAnimalEtatAmi.instance = new SingeAnimalEtatAmi();
        return SingeAnimalEtatAmi.instance;
    }

    @Override
    public void deplacer(Animal animal, Jeu jeu) {
        throw new AnimalEtatException("Comportement non-spécifié.");
    }

    @Override
    public void prendreCoup(Animal animal) {
        animal.changerEtat(SingeAnimalEtatRassasie.obtenirInstance());
    }

    @Override
    public String toString() {
        return SingeAnimalEtatAmi.AFFICHAGE;
    }
}
