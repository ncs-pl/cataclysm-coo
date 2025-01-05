package modele;

import vue.Ihm;

public class SingeAnimalEtatPerche extends AnimalEtatDecorateur {
    public static final String AFFICHAGE = Ihm.COULEUR_FOND_JAUNE + Ihm.COULEUR_VERT + Singe.SYMBOLE + Ihm.COULEUR_REINITIALISATION;


    public SingeAnimalEtatPerche(AnimalEtat animalEtat) {
        super(animalEtat);
    }

    @Override
    public void deplacer(Animal animal, Jeu jeu){
        animal.changerEtat(this.animalEtat);
        super.deplacer(animal, jeu);
    }

    @Override
    public String toString() {
        return SingeAnimalEtatPerche.AFFICHAGE;
    }
}
