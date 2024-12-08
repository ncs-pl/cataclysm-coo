package modele;

public class EcureuilAnimalEtatRassasie extends AnimalEtat {
    private EcureuilAnimalEtatRassasie() {
        super(AnimalEtat.ETAT_RASSASIE);
    }

    /** Obtient l'instance singleton de l'état. */
    public static AnimalEtat obtenirInstance() {
        if (EcureuilAnimalEtatRassasie.instance == null) EcureuilAnimalEtatRassasie.instance = new EcureuilAnimalEtatRassasie();
        return EcureuilAnimalEtatRassasie.instance;
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
            animal.changerEtat(EcureuilAnimalEtatAffame.obtenirInstance());
        }
    }

    @Override public void prendreCoup(Animal animal) {
        throw new AnimalEtatException("Comportement non-spécifié.");
    }
}
