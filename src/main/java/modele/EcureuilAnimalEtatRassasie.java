package modele;

import vue.Ihm;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class EcureuilAnimalEtatRassasie extends AnimalEtat {
    public static final String AFFICHAGE = Ihm.COULEUR_FOND_JAUNE + Ihm.COULEUR_BLEU + Ecureuil.SYMBOLE + Ihm.COULEUR_REINITIALISATION;

    private static EcureuilAnimalEtatRassasie instance; // Singleton
    private EcureuilAnimalEtatRassasie() { super(AnimalEtat.ETAT_RASSASIE); }

    /** Obtient l'instance singleton de l'état. */
    public static AnimalEtat obtenirInstance() {
        if (EcureuilAnimalEtatRassasie.instance == null) EcureuilAnimalEtatRassasie.instance = new EcureuilAnimalEtatRassasie();
        return EcureuilAnimalEtatRassasie.instance;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void deplacer(Animal animal, Jeu jeu) {
        int ligne   = animal.obtenirLigne();
        int colonne = animal.obtenirColonne();

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
                } else {
                    if (jeu.verifierCaseDecors(ligne, colonne)) {
                        zones = new ArrayList<>();
                    } else {
                        zones = List.of(Objects.requireNonNull(choixDirectionFuite(jeu, zones, predateur.obtenirLigne(),
                                predateur.obtenirColonne(), ligne, colonne)));
                    }
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

        int saturation = animal.obtenirSaturation();
        if (saturation == 0){
            if (jeu.verifierCaseDecors(ligne, colonne)) {
                int type = jeu.obtenirCaseDecors(ligne, colonne).obtenirType();
                if (type == Acteur.TYPE_ARBRE){
                    if(animal.obtenirAmitie()>=1){
                        animal.changerEtat(new EcureuilAnimalEtatPerche(new EcureuilAnimalEtatAmi(EcureuilAnimalEtatAffame.obtenirInstance())));
                    }
                    else {
                        animal.changerEtat(new EcureuilAnimalEtatPerche(EcureuilAnimalEtatAffame.obtenirInstance()));
                    }
                } else if (type == Acteur.TYPE_BUISSON){
                    if(animal.obtenirAmitie()>=1){
                        animal.changerEtat(new EcureuilAnimalEtatCache(new EcureuilAnimalEtatAmi(EcureuilAnimalEtatAffame.obtenirInstance())));
                    }
                    else {
                        animal.changerEtat(new EcureuilAnimalEtatCache(EcureuilAnimalEtatAffame.obtenirInstance()));
                    }
                } else {
                    animal.changerEtat(EcureuilAnimalEtatAffame.obtenirInstance());
                }
            } else {
                if(animal.obtenirAmitie()>=1){
                    animal.changerEtat(new EcureuilAnimalEtatAmi(EcureuilAnimalEtatAffame.obtenirInstance()));
                }
                else {
                    animal.changerEtat(EcureuilAnimalEtatAffame.obtenirInstance());
                }
            }
        } else {
            animal.changerSaturation(saturation - 1);

            if (jeu.verifierCaseDecors(ligne, colonne)) {
                int type = jeu.obtenirCaseDecors(ligne, colonne).obtenirType();
                if(type == Acteur.TYPE_ARBRE){
                    if(animal.obtenirAmitie()>=1){
                        animal.changerEtat(new EcureuilAnimalEtatPerche(new EcureuilAnimalEtatAmi(EcureuilAnimalEtatRassasie.obtenirInstance())));
                    }
                    else {
                        animal.changerEtat(new EcureuilAnimalEtatPerche(EcureuilAnimalEtatRassasie.obtenirInstance()));
                    }
                } else if (type == Acteur.TYPE_BUISSON){
                    if(animal.obtenirAmitie()>=1){
                        animal.changerEtat(new EcureuilAnimalEtatCache(new EcureuilAnimalEtatAmi(EcureuilAnimalEtatRassasie.obtenirInstance())));
                    }
                    else {
                        animal.changerEtat(new EcureuilAnimalEtatCache(EcureuilAnimalEtatRassasie.obtenirInstance()));
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
        return EcureuilAnimalEtatRassasie.AFFICHAGE;
    }
}
