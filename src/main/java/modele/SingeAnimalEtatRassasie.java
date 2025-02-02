package modele;

import vue.Ihm;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class SingeAnimalEtatRassasie extends AnimalEtat {
    public static final String AFFICHAGE = Ihm.COULEUR_FOND_JAUNE + Ihm.COULEUR_BLEU + Singe.SYMBOLE + Ihm.COULEUR_REINITIALISATION;

    private static SingeAnimalEtatRassasie instance; // Singleton
    private SingeAnimalEtatRassasie() { super(AnimalEtat.ETAT_RASSASIE); }

    /** Obtient l'instance singleton de l'état. */
    public static AnimalEtat obtenirInstance() {
        if (SingeAnimalEtatRassasie.instance == null) SingeAnimalEtatRassasie.instance = new SingeAnimalEtatRassasie();
        return SingeAnimalEtatRassasie.instance;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void deplacer(Animal animal, Jeu jeu) {
        int ligne   = animal.obtenirLigne();
        int colonne = animal.obtenirColonne();

        List<Acteur> zones = jeu.chercherDecorsVoisins(ligne, colonne);
        zones.addAll(jeu.chercherZonesVidesVoisine(ligne, colonne));
        Predateur predateur = jeu.chercherPredateur(ligne, colonne);

        if (predateur != null && !(predateur.obtenirType() == Acteur.TYPE_SCORPION
                && jeu.verifierTypeCaseDecors(predateur.obtenirLigne(), predateur.obtenirColonne(),
                Acteur.TYPE_PETIT_ROCHER))) {
            if(jeu.verifierTypeCaseDecors(ligne, colonne, Acteur.TYPE_COCOTIER)) {
                zones = new ArrayList<>();
            } else {
                Acteur cache = null;

                for (Acteur acteur : zones) {
                    if (acteur.obtenirType() == Acteur.TYPE_COCOTIER) {
                        cache = acteur;
                        break;
                    } else if (acteur.obtenirType() == Acteur.TYPE_PETIT_ROCHER) {
                        cache = acteur;
                    }
                }
                if (cache != null) {
                    zones = List.of(cache);
                } else {
                    if (jeu.verifierCaseDecors(ligne, colonne)) {
                        zones = new ArrayList<>();
                    } else {
                        zones = List.of(Objects.requireNonNull(choixDirectionFuite(jeu, zones,
                                predateur.obtenirLigne(), predateur.obtenirColonne(),
                                ligne, colonne)));
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

        ligne = animal.obtenirLigne();
        colonne = animal.obtenirColonne();

        int saturation = animal.obtenirSaturation();

        if (saturation == 0){
            if(animal.obtenirAmitie() >= 2){
                animal.changerEtat(new SingeAnimalEtatAmi(SingeAnimalEtatAffame.obtenirInstance()));
            } else {
                animal.changerEtat(SingeAnimalEtatAffame.obtenirInstance());
                }
        } else {
            animal.changerSaturation(saturation - 1);
        }

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
        return SingeAnimalEtatRassasie.AFFICHAGE;
    }
}
