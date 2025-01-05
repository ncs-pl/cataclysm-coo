package modele;

import vue.Ihm;

public class EcureuilAnimalEtatCache extends AnimalEtatDecorateur {
    public static final String AFFICHAGE = Ihm.COULEUR_FOND_JAUNE + Ihm.COULEUR_JAUNE + Ecureuil.SYMBOLE + Ihm.COULEUR_REINITIALISATION;


    public EcureuilAnimalEtatCache(AnimalEtat animalEtat) {
        super(animalEtat);
    }

    @Override
    public void deplacer(Animal animal, Jeu jeu) {
        animal.changerEtat(this.animalEtat);
        super.deplacer(animal, jeu);
    }

    @Override
    public String toString() {
        return EcureuilAnimalEtatCache.AFFICHAGE;
    }
}
