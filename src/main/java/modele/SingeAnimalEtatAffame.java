package modele;

import vue.Ihm;

import java.util.List;
import java.util.Random;

public class SingeAnimalEtatAffame extends AnimalEtat {
    public static final String AFFICHAGE = Ihm.COULEUR_FOND_JAUNE + Ihm.COULEUR_NOIR + Singe.SYMBOLE + Ihm.COULEUR_REINITIALISATION;

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
        Objet cHallu = jeu.chercherObjetVoisin(ligne, colonne, Acteur.TYPE_CHAMPIGNON_HALLUCINOGENE);

        if (banane != null || champignon != null || cHallu != null) {
            animal.changerSaturation(3);

            // Se déplacer sur la case de la nourriture et supprimer l'objet.
            Objet nourriture = banane == null ? ( champignon == null ? cHallu : champignon) : banane;

            animal.changerLigne(nourriture.obtenirLigne());
            animal.changerColonne(nourriture.obtenirColonne());
            jeu.supprimerObjet(nourriture);

            if (nourriture == cHallu){
                animal.changerEtat(SingeAnimalEtatJunkie.obtenirInstance());
            } else {
                animal.changerEtat(SingeAnimalEtatRassasie.obtenirInstance());
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

            if (!zones.isEmpty()) {
                Random rand = new Random();
                Acteur next = zones.get(rand.nextInt(zones.size()));

                animal.changerLigne(next.obtenirLigne());
                animal.changerColonne(next.obtenirColonne());
            }
        }

        ligne = animal.obtenirLigne();
        colonne = animal.obtenirColonne();

        if (jeu.verifierCaseDecors(ligne, colonne)) {
            int type = jeu.obtenirCaseDecors(ligne, colonne).obtenirType();
            if (type == Acteur.TYPE_COCOTIER){
                animal.changerEtat(new SingeAnimalEtatPerche(animal.obtenirEtat()));
            } else if (type == Acteur.TYPE_PETIT_ROCHER){
                animal.changerEtat(new SingeAnimalEtatCache(animal.obtenirEtat()));
            }
        }
    }

    @Override
    public void prendreCoup(Animal animal) {
        throw new AnimalEtatException("Comportement non-spécifié.");
    }

    @Override
    public String toString() {
        return SingeAnimalEtatAffame.AFFICHAGE;
    }
}
