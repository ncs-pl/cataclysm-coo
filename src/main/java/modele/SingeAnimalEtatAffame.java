package modele;

import vue.Ihm;

import java.util.List;
import java.util.Random;

public class SingeAnimalEtatAffame extends AnimalEtat {
    private static SingeAnimalEtatAffame instance; // Singleton

    private SingeAnimalEtatAffame() {
        super(AnimalEtat.ETAT_AFFAME);
    }

    /** Obtient l'instance singleton de l'état. */
     public static AnimalEtat obtenirInstance() {
        if (SingeAnimalEtatAffame.instance == null) SingeAnimalEtatAffame.instance = new SingeAnimalEtatAffame();
        return SingeAnimalEtatAffame.instance;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void deplacer(Animal animal, Jeu jeu) {
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

            if (banane != null){
                animal.changerEtat(SingeAnimalEtatRassasie.obtenirInstance());
            } else {
                animal.changerEtat(SingeAnimalEtatJunkie.obtenirInstance());
            }

            // Vérifier pour probable nouvelle amitié.

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

            List<Acteur> zones = jeu.chercherDecorsVoisins(ligne, colonne);
            zones.addAll(jeu.chercherZonesVidesVoisine(ligne, colonne));

            int decors = -1;

            if (!zones.isEmpty()) {
                Random rand = new Random();
                Acteur next = zones.get(rand.nextInt(zones.size()));

                if (next.obtenirType() == Acteur.TYPE_COCOTIER || next.obtenirType() == Acteur.TYPE_PETIT_ROCHER) {
                    decors = next.obtenirType();
                }

                animal.changerLigne(next.obtenirLigne());
                animal.changerColonne(next.obtenirColonne());
            }

            if (decors == Acteur.TYPE_COCOTIER || decors == Acteur.TYPE_PETIT_ROCHER) {
                animal.changerEtat(new SingeAnimalEtatPerche(SingeAnimalEtatAffame.obtenirInstance()));
            }
        }
    }

    @Override
    public void prendreCoup(Animal animal) {
        throw new AnimalEtatException("Comportement non-spécifié.");
    }

    @Override
    public String toString() {
        return Ihm.COULEUR_FOND_JAUNE + Ihm.COULEUR_NOIR + Singe.SYMBOLE + Ihm.COULEUR_REINITIALISATION;
    }
}
