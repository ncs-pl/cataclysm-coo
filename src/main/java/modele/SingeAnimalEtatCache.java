package modele;

import vue.Ihm;

public class SingeAnimalEtatCache extends AnimalEtat {
    private static SingeAnimalEtatCache instance; // Singleton

    private SingeAnimalEtatCache() {
        super(AnimalEtat.ETAT_CACHE);
    }

    /** Obtient l'instance singleton de l'état. */
    public static AnimalEtat obtenirInstance() {
        if (SingeAnimalEtatCache.instance == null) SingeAnimalEtatCache.instance = new SingeAnimalEtatCache();
        return SingeAnimalEtatCache.instance;
    }

    @Override
    public void deplacer(Animal animal, Jeu jeu) {
        throw new AnimalEtatException("Pas encore implémenté.");
    }

    @Override
    public void prendreCoup(Animal animal) {
        throw new AnimalEtatException("Pas encore implémenté.");
    }

    @Override
    public String toString() {
        return Ihm.COULEUR_FOND_JAUNE + Ihm.COULEUR_JAUNE + Singe.SYMBOLE + Ihm.COULEUR_REINITIALISATION;
    }
}
