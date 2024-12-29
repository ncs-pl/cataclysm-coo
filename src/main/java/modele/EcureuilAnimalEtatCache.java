package modele;

import vue.Ihm;

public class EcureuilAnimalEtatCache extends AnimalEtatDecorateur {

    public EcureuilAnimalEtatCache(AnimalEtat animalEtat) {
        super(animalEtat);
    }

    @Override
    public String toString() {
        String temp = super.toString();
        return temp.substring(0, 5) +
                Ihm.COULEUR_JAUNE +
                temp.substring(10);
    }
}
