package modele;

import vue.Ihm;

public class EcureuilAnimalEtatPerche extends AnimalEtatDecorateur {

    public EcureuilAnimalEtatPerche(AnimalEtat animalEtat) {
        super(animalEtat);
    }

    @Override
    public void deplacer(Animal animal, Jeu jeu) {
        animal.changerEtat(this.animalEtat);
        super.deplacer(animal, jeu);
    }

    @Override
    public String toString() {
        String temp = super.toString();
        return temp.substring(0, 5) +
                Ihm.COULEUR_VERT +
                temp.substring(10);
    }
}
