package modele;

import vue.Ihm;

public class EcureuilAnimalEtatAmi extends AnimalEtatDecorateur {
    public static final String AFFICHAGE = Ihm.COULEUR_FOND_JAUNE + Ihm.COULEUR_VIOLET + Ecureuil.SYMBOLE + Ihm.COULEUR_REINITIALISATION;
    public EcureuilAnimalEtatAmi(AnimalEtat animalEtat ) {
        super(animalEtat);
    }

    @Override
    public void deplacer(Animal animal, Jeu jeu) {
        super.deplacer(animal,jeu);
    }

    @Override
    public void prendreCoup(Animal animal) {
        animal.changerEtat(EcureuilAnimalEtatRassasie.obtenirInstance());
    }

    @Override
    public String toString() {
        return EcureuilAnimalEtatAmi.AFFICHAGE;
    }
}
