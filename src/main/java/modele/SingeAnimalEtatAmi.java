package modele;

import vue.Ihm;

public class SingeAnimalEtatAmi extends AnimalEtatDecorateur {
    public static final String AFFICHAGE = Ihm.COULEUR_FOND_JAUNE + Ihm.COULEUR_VIOLET + Singe.SYMBOLE + Ihm.COULEUR_REINITIALISATION;

    private AnimalEtat animalEtat;

    public SingeAnimalEtatAmi(AnimalEtat animalEtat) {
        super(animalEtat);
    }

    @Override
    public void deplacer(Animal animal, Jeu jeu) {
        super.deplacer(animal,jeu);
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
