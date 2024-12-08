package modele;

public class SingeAnimalEtatAffame extends AnimalEtat {
    private SingeAnimalEtatAffame() {
        super(AnimalEtat.ETAT_AFFAME);
    }

    /** Obtient l'instance singleton de l'état. */
     public static AnimalEtat obtenirInstance() {
        if (SingeAnimalEtatAffame.instance == null) SingeAnimalEtatAffame.instance = new SingeAnimalEtatAffame();
        return SingeAnimalEtatAffame.instance;
    }

    @Override public void deplacer(Animal animal, Jeu jeu) {
        int ligne   = animal.obtenirLigne();
        int colonne = animal.obtenirColonne();

        // Chercher nourriture proche.

        // TODO(nico): supprimer l'objet et déplacer l'animal sur la case
        Banane banane         = jeu.chercherBananeVoisine(ligne, colonne);
        Champignon champignon = jeu.chercherChampignonVoisin(ligne, colonne);
        if (banane != null || champignon != null) {
            animal.changerSaturation(3);

            // Vérifier pour probable nouvelle amitié.
            int amitie = animal.obtenirAmitie();
            if (jeu.chercherPersonnageVoisin(ligne, colonne)) amitie += 1;
            animal.changerAmitie(amitie);

            AnimalEtat etat = SingeAnimalEtatRassasie.obtenirInstance();
            if (amitie >= 2) {
                animal.changerAmitie(0);
                etat = SingeAnimalEtatAmi.obtenirInstance();
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
