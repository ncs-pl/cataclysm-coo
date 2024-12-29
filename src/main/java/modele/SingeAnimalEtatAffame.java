package modele;

import java.util.List;
import java.util.Random;

public class SingeAnimalEtatAffame extends AnimalEtat {

    private static SingeAnimalEtatAffame instance;

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

        // Chercher nourriture proche.

        Objet banane         = jeu.chercherObjetVoisin(ligne, colonne, Acteur.TYPE_BANANE);
        Objet champignon = jeu.chercherObjetVoisin(ligne, colonne, Acteur.TYPE_CHAMPIGNON);
        if (banane != null || champignon != null) {
            animal.changerSaturation(3);

            // Se déplacer sur la case de la nourriture et supprimer l'objet.
            Objet nourriture = banane == null ? champignon : banane;

            animal.changerLigne(nourriture.obtenirLigne());
            animal.changerColonne(nourriture.obtenirColonne());
            jeu.supprimerObjet(nourriture);

            // Vérifier pour probable nouvelle amitié.

            animal.changerEtat(SingeAnimalEtatRassasie.obtenirInstance());


            int amitie = animal.obtenirAmitie();
            if (jeu.chercherPersonnageVoisin(ligne, colonne)) amitie += 1;
            animal.changerAmitie(amitie);

            /*
            if (amitie >= 2) {
                //TODO effectuer et gerer changement etat ami
                animal.changerEtat(SingeAnimalEtatAmi.obtenirInstance());
            }*/

        } else {

            // Sinon se déplacer aléatoirement.

            List<ZoneVide> vides = jeu.chercherZonesVidesVoisine(ligne, colonne);
            if (!vides.isEmpty()) {
                Random rand = new Random();
                Acteur vide = vides.get(rand.nextInt(vides.size()));
                animal.changerLigne(vide.obtenirLigne());
                animal.changerColonne(vide.obtenirColonne());
            }
        }
    }

    @Override public void prendreCoup(Animal animal) {
        throw new AnimalEtatException("Comportement non-spécifié.");
    }

    @Override
    public String toString() {
        return "";
    }
}
