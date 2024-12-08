package modele;

public class SingeAnimalEtatRassasie extends AnimalEtat {
    private SingeAnimalEtatRassasie() {
        super(AnimalEtat.ETAT_RASSASIE);
    }

    /** Obtient l'instance singleton de l'état. */
    public static AnimalEtat obtenirInstance() {
        if (SingeAnimalEtatRassasie.instance == null) SingeAnimalEtatRassasie.instance = new SingeAnimalEtatRassasie();
        return SingeAnimalEtatRassasie.instance;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override public void deplacer(Animal animal, Jeu jeu) {
        int ligne   = animal.obtenirLigne();
        int colonne = animal.obtenirColonne();

        ZoneVide vide = jeu.chercherZoneVideVoisine(ligne, colonne);
        if (vide != null) {
            animal.changerLigne(vide.obtenirLigne());
            animal.changerColonne(vide.obtenirColonne());
        }

        int saturation = animal.obtenirSaturation();
        animal.changerSaturation(saturation - 1);

        if (saturation == 0) {
            animal.changerEtat(SingeAnimalEtatAffame.obtenirInstance());
        }
    }

    @Override public void prendreCoup(Animal animal) {
        throw new AnimalEtatException("Comportement non-spécifié.");
    }
}
