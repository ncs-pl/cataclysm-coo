package modele;

import vue.Ihm;

public class EcureuilAnimalEtatPerche extends AnimalEtatDecorateur {
    public static final String AFFICHAGE = Ihm.COULEUR_FOND_JAUNE + Ihm.COULEUR_VERT + Ecureuil.SYMBOLE + Ihm.COULEUR_REINITIALISATION;


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
        return EcureuilAnimalEtatPerche.AFFICHAGE;
    }
}
