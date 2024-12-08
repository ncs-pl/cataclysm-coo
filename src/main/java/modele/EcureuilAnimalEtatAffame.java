package modele;

public class EcureuilAnimalEtatAffame extends AnimalEtat {
    private EcureuilAnimalEtatAffame() {
        super(AnimalEtat.ETAT_AFFAME);
    }

    /** Obtient l'instance singleton de l'état. */
    public static AnimalEtat obtenirInstance() {
        if (EcureuilAnimalEtatAffame.instance == null) EcureuilAnimalEtatAffame.instance = new EcureuilAnimalEtatAffame();
        return EcureuilAnimalEtatAffame.instance;
    }

    @Override public void deplacer(Animal animal, Jeu jeu) {
        int ligne   = animal.obtenirLigne();
        int colonne = animal.obtenirColonne();

        // Chercher nourriture proche.

        // TODO(nico): supprimer l'objet et déplacer l'animal sur la case
        Gland gland           = jeu.chercherGlandVoisin(ligne, colonne);
        Champignon champignon = jeu.chercherChampignonVoisin(ligne, colonne);
        if (gland != null || champignon != null) {
            animal.changerSaturation(5);

            // Vérifier pour probable nouvelle amitié.
            int amitie = animal.obtenirAmitie();
            if (jeu.chercherPersonnageVoisin(ligne, colonne)) amitie += 1;
            animal.changerAmitie(amitie);

            AnimalEtat etat = EcureuilAnimalEtatRassasie.obtenirInstance();
            if (amitie >= 1) {
                animal.changerAmitie(0);
                etat = EcureuilAnimalEtatAmi.obtenirInstance();
            }
            animal.changerEtat(etat);
            return;
        }

        // Sinon se déplacer aléatoirement.
        ZoneVide vide = jeu.chercherZoneVideVoisine(ligne, colonne);
        if (vide != null) {
            animal.changerLigne(vide.obtenirLigne());
            animal.changerColonne(vide.obtenirColonne());
        }
    }

    @Override public void prendreCoup(Animal animal) {
        throw new AnimalEtatException("Comportement non-spécifié.");
    }
}
