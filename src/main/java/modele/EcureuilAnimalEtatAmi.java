package modele;

import vue.Ihm;

public class EcureuilAnimalEtatAmi extends AnimalEtat {
    public static final String AFFICHAGE = Ihm.COULEUR_FOND_JAUNE + Ihm.COULEUR_VIOLET + Ecureuil.SYMBOLE + Ihm.COULEUR_REINITIALISATION;
    private static EcureuilAnimalEtatAmi instance; // Singleton
    private EcureuilAnimalEtatAmi() { super(AnimalEtat.ETAT_AMI); }

    /** Obtient l'instance singleton de l'état. */
    public static AnimalEtat obtenirInstance() {
        if (EcureuilAnimalEtatAmi.instance == null) EcureuilAnimalEtatAmi.instance = new EcureuilAnimalEtatAmi();
        return EcureuilAnimalEtatAmi.instance;
    }

    @Override
    public void deplacer(Animal animal, Jeu jeu) {
        throw new AnimalEtatException("Comportement non-spécifié.");
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
