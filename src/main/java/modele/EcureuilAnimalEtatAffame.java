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

            List<Acteur> zones = jeu.chercherDecorsVoisins(ligne, colonne);
            zones.addAll(jeu.chercherZonesVidesVoisine(ligne, colonne));

            int decors = -1;

            if (!zones.isEmpty()) {
                Random rand = new Random();
                Acteur next = zones.get(rand.nextInt(zones.size()));

                if (next.obtenirType() == Acteur.TYPE_ARBRE || next.obtenirType() == Acteur.TYPE_BUISSON) {
                    decors = next.obtenirType();
                }

                animal.changerLigne(next.obtenirLigne());
                animal.changerColonne(next.obtenirColonne());
            }

            if(decors == Acteur.TYPE_ARBRE) {
                animal.changerEtat(new EcureuilAnimalEtatPerche(EcureuilAnimalEtatAffame.obtenirInstance()));
            } else if (decors == Acteur.TYPE_BUISSON) {
                animal.changerEtat(new EcureuilAnimalEtatCache(EcureuilAnimalEtatAffame.obtenirInstance()));
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
