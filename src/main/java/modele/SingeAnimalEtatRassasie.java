package modele;

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

        List<ZoneVide> vides = jeu.chercherZoneVideVoisine(ligne, colonne);
        if (!vides.isEmpty()) {
            Random rand = new Random();
            Acteur vide = vides.get(rand.nextInt(vides.size()));
            animal.changerLigne(vide.obtenirLigne());
            animal.changerColonne(vide.obtenirColonne());
        }

        int saturation = animal.obtenirSaturation();
        if (saturation == 0){
            animal.changerEtat(SingeAnimalEtatAffame.obtenirInstance());
        } else {
            animal.changerSaturation(saturation - 1);
        }
    }

    @Override public void prendreCoup(Animal animal) {
        throw new AnimalEtatException("Comportement non-spécifié.");
    }
}
