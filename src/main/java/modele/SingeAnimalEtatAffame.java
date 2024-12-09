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

    @SuppressWarnings("DuplicatedCode")
    @Override public void deplacer(Animal animal, Jeu jeu) {
        int ligne   = animal.obtenirLigne();
        int colonne = animal.obtenirColonne();
        assert(ligne >= 0 && ligne < 35); // tmp

        // Chercher nourriture proche.

        Banane banane         = jeu.chercherBananeVoisine(ligne, colonne);
        Champignon champignon = jeu.chercherChampignonVoisin(ligne, colonne);
        if (banane != null || champignon != null) {
            animal.changerSaturation(3);

            // Se déplacer sur la case de la nourriture et supprimer l'objet.
            Objet nourriture = banane;
            if (banane == null) nourriture = champignon;
            animal.changerLigne(nourriture.obtenirLigne());
            animal.changerColonne(nourriture.obtenirColonne());
            jeu.supprimerObjet(nourriture);

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
