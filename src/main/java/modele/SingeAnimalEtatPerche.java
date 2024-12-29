package modele;

import vue.Ihm;

public class SingeAnimalEtatPerche extends AnimalEtatDecorateur {

    public SingeAnimalEtatPerche(AnimalEtat animalEtat) {
        super(animalEtat);
    }

    @Override
    public String toString() {
        String temp = super.toString();
        return temp.substring(0, 5) +
                Ihm.COULEUR_VERT +
                temp.substring(10);
    }
}
