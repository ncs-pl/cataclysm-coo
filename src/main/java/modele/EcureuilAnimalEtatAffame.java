package modele;
import vue.Ihm;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class EcureuilAnimalEtatAffame extends AnimalEtat {
    public static final String AFFICHAGE = Ihm.COULEUR_FOND_JAUNE + Ihm.COULEUR_NOIR + Ecureuil.SYMBOLE + Ihm.COULEUR_REINITIALISATION;

    private static EcureuilAnimalEtatAffame instance; // Singleton

    private EcureuilAnimalEtatAffame() {
        super(AnimalEtat.ETAT_AFFAME);
    }

    /** Obtient l'instance singleton de l'état. */
    public static AnimalEtat obtenirInstance() {
        if (EcureuilAnimalEtatAffame.instance == null) EcureuilAnimalEtatAffame.instance = new EcureuilAnimalEtatAffame();
        return EcureuilAnimalEtatAffame.instance;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void deplacer(Animal animal, Jeu jeu) {
        int ligne   = animal.obtenirLigne();
        int colonne = animal.obtenirColonne();

        // Chercher nourriture proche.


        Objet gland           = jeu.chercherObjetVoisin(ligne, colonne, Acteur.TYPE_GLAND);
        Objet champignon = jeu.chercherObjetVoisin(ligne, colonne, Acteur.TYPE_CHAMPIGNON);
        Objet cVeneneux = jeu.chercherObjetVoisin(ligne, colonne, Acteur.TYPE_CHAMPIGNON_VENENEUX);

        if (gland != null || champignon != null || cVeneneux != null) {
            animal.changerSaturation(5);

            // Se déplacer sur la case de la nourriture et supprimer l'objet.
            Objet nourriture = gland == null ? (champignon == null ? cVeneneux : champignon) : gland;


            int newLigne = nourriture.obtenirLigne();
            int newColonne = nourriture.obtenirColonne();
            animal.changerLigne(newLigne);
            animal.changerColonne(newColonne);
            jeu.supprimerObjet(nourriture);

            if (cVeneneux == nourriture){
                animal.changerEtat(EcureuilAnimalEtatJunkie.obtenirInstance());
            } else {
                animal.changerEtat(EcureuilAnimalEtatRassasie.obtenirInstance());
            }

            // Vérifier pour probable nouvelle amitié.
            int amitie = animal.obtenirAmitie();
            if (jeu.chercherPersonnageVoisin(newLigne, newColonne)) amitie += 1;
            animal.changerAmitie(amitie);

            if (amitie >= 1) {
                //TODO effectuer le changement d'Etat
                animal.changerEtat(new EcureuilAnimalEtatAmi(EcureuilAnimalEtatAffame.obtenirInstance()));

            }
        } else {
            // Sinon se déplacer aléatoirement.
            List<Acteur> zones = jeu.chercherDecorsVoisins(ligne, colonne);
            zones.addAll(jeu.chercherZonesVidesVoisine(ligne, colonne));
            Predateur predateur = jeu.chercherPredateur(ligne, colonne);

            if (predateur != null) {
                if (jeu.verifierTypeCaseDecors(ligne, colonne, Acteur.TYPE_ARBRE)) {
                    zones = new ArrayList<>();
                } else {
                    //TODO : Gestion ami a faire
                    Acteur cache = null;

                    for (Acteur acteur : zones) {
                        if (acteur.obtenirType() == Acteur.TYPE_ARBRE){
                            cache = acteur;
                            break;
                        } else if (acteur.obtenirType() == Acteur.TYPE_BUISSON){
                            cache = acteur;
                        }
                    }
                    if (cache != null) {
                        zones = List.of(cache);
                    } else if (jeu.verifierCaseDecors(ligne, colonne)) {
                        zones = new ArrayList<>();
                    } else {
                        zones = List.of(Objects.requireNonNull(choixDirectionFuite(jeu, zones, predateur.obtenirLigne(),
                                predateur.obtenirColonne(), ligne, colonne)));
                    }
                }
            }

            if (!zones.isEmpty()) {
                Random rand = new Random();
                Acteur next = zones.get(rand.nextInt(zones.size()));

                animal.changerLigne(next.obtenirLigne());
                animal.changerColonne(next.obtenirColonne());
            }

            ligne   = animal.obtenirLigne();
            colonne = animal.obtenirColonne();

            if (jeu.verifierCaseDecors(ligne, colonne)) {
                int type = jeu.obtenirCaseDecors(ligne, colonne).obtenirType();
                if (type == Acteur.TYPE_ARBRE){
                    if(animal.obtenirAmitie()>=1){
                        animal.changerEtat(new EcureuilAnimalEtatPerche(new EcureuilAnimalEtatAmi(EcureuilAnimalEtatAffame.obtenirInstance())));
                    }
                    else{
                        animal.changerEtat(new EcureuilAnimalEtatPerche(EcureuilAnimalEtatAffame.obtenirInstance()));
                    }
                } else if (type == Acteur.TYPE_BUISSON){
                    if(animal.obtenirAmitie()>=1){
                        animal.changerEtat(new EcureuilAnimalEtatCache(new EcureuilAnimalEtatAmi(EcureuilAnimalEtatAffame.obtenirInstance())));
                    }
                    else{
                        animal.changerEtat(new EcureuilAnimalEtatCache(EcureuilAnimalEtatAffame.obtenirInstance()));
                    }
                }

            }
        }
    }

    @Override
    public void prendreCoup(Animal animal) {
        throw new AnimalEtatException("Comportement non-spécifié.");
    }

    @Override
    public String toString() {
        return EcureuilAnimalEtatAffame.AFFICHAGE;
    }
}
