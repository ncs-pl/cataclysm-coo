package modele;

import vue.Ihm;

import java.util.List;
import java.util.Random;

public class SingeAnimalEtatRassasie extends AnimalEtat {

    private static SingeAnimalEtatRassasie instance;

    private SingeAnimalEtatRassasie() {
        super(AnimalEtat.ETAT_RASSASIE);
    }

    /** Obtient l'instance singleton de l'état. */
    public static AnimalEtat obtenirInstance() {
        if (SingeAnimalEtatRassasie.instance == null) SingeAnimalEtatRassasie.instance = new SingeAnimalEtatRassasie();
        return SingeAnimalEtatRassasie.instance;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override public void deplacer(Animal animal, Jeu jeu) {
        int ligne   = animal.obtenirLigne();
        int colonne = animal.obtenirColonne();

        List<Acteur> zones = jeu.chercherDecorsVoisins(ligne, colonne);
        zones.addAll(jeu.chercherZonesVidesVoisine(ligne, colonne));

        int decors = -1;

        if (!zones.isEmpty()) {
            Random rand = new Random();
            Acteur next = zones.get(rand.nextInt(zones.size()));

            if (next.obtenirType() == Acteur.TYPE_COCOTIER || next.obtenirType() == Acteur.TYPE_PETIT_ROCHER){
                decors = next.obtenirType();
            }

            animal.changerLigne(next.obtenirLigne());
            animal.changerColonne(next.obtenirColonne());
        }

        int saturation = animal.obtenirSaturation();

        if (saturation == 0){
            if(decors == Acteur.TYPE_COCOTIER || decors == Acteur.TYPE_PETIT_ROCHER){
                animal.changerEtat(new SingeAnimalEtatPerche(SingeAnimalEtatAffame.obtenirInstance()));
            } else {
                animal.changerEtat(SingeAnimalEtatAffame.obtenirInstance());
            }
        } else {
            animal.changerSaturation(saturation - 1);
        }

        if (decors == Acteur.TYPE_COCOTIER || decors == Acteur.TYPE_PETIT_ROCHER) {
            animal.changerEtat(new SingeAnimalEtatPerche(SingeAnimalEtatRassasie.obtenirInstance()));
        }
    }

    @Override public void prendreCoup(Animal animal) {
        throw new AnimalEtatException("Comportement non-spécifié.");
    }

    @Override
    public String toString() {
        return Ihm.COULEUR_FOND_JAUNE      +
                Ihm.COULEUR_BLEU            +
                Acteur.SYMBOLE_SINGE         +
                Ihm.COULEUR_REINITIALISATION;
    }
}
