package modele;
import vue.Ihm;

import java.util.List;
import java.util.Random;

public class EcureuilAnimalEtatAffame extends AnimalEtat {

    private static EcureuilAnimalEtatAffame instance;

    private EcureuilAnimalEtatAffame() {
        super(AnimalEtat.ETAT_AFFAME);
    }

    /** Obtient l'instance singleton de l'état. */
    public static AnimalEtat obtenirInstance() {
        if (EcureuilAnimalEtatAffame.instance == null) EcureuilAnimalEtatAffame.instance = new EcureuilAnimalEtatAffame();
        return EcureuilAnimalEtatAffame.instance;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override public void deplacer(Animal animal, Jeu jeu) {
        int ligne   = animal.obtenirLigne();
        int colonne = animal.obtenirColonne();

        // Chercher nourriture proche.


        Objet gland           = jeu.chercherObjetVoisin(ligne, colonne, Acteur.TYPE_GLAND);
        Objet champignon = jeu.chercherObjetVoisin(ligne, colonne, Acteur.TYPE_CHAMPIGNON);
        if (gland != null || champignon != null) {
            animal.changerSaturation(5);

            // Se déplacer sur la case de la nourriture et supprimer l'objet.
            Objet nourriture = gland == null ? champignon : gland;

            animal.changerLigne(nourriture.obtenirLigne());
            animal.changerColonne(nourriture.obtenirColonne());
            jeu.supprimerObjet(nourriture);

            if (gland != null){
                animal.changerEtat(EcureuilAnimalEtatRassasie.obtenirInstance());
            } else {
                animal.changerEtat(EcureuilAnimalEtatJunkie.obtenirInstance());
            }

            // Vérifier pour probable nouvelle amitié.
            int amitie = animal.obtenirAmitie();
            if (jeu.chercherPersonnageVoisin(ligne, colonne)) amitie += 1;
            animal.changerAmitie(amitie);

            if (amitie >= 1) {
                /* TODO effectuer le changement d'Etat
                animal.changerEtat(EcureuilAnimalEtatAmi.obtenirInstance());
                 */
            }
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
        return Ihm.COULEUR_FOND_JAUNE      +
                Ihm.COULEUR_NOIR            +
                Acteur.SYMBOLE_ECUREUIL      +
                Ihm.COULEUR_REINITIALISATION;
    }
}
