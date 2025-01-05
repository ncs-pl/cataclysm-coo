package modele;

import vue.Ihm;

import java.util.List;
import java.util.Random;

public class SingeAnimalEtatJunkie extends AnimalEtat {

    private static SingeAnimalEtatJunkie instance;

    private SingeAnimalEtatJunkie() {
        super(AnimalEtat.ETAT_JUNKIE);
    }

    /** Obtient l'instance singleton de l'état. */
    public static AnimalEtat obtenirInstance() {
        if (SingeAnimalEtatJunkie.instance == null) SingeAnimalEtatJunkie.instance = new SingeAnimalEtatJunkie();
        return SingeAnimalEtatJunkie.instance;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override public void deplacer(Animal animal, Jeu jeu) {
        
        //TODO: Forcer l'animal à ne pas revenir sur ces pas ?

        int decors = -1;

        for (int i = 0; i < 2; ++i) {

            int ligne   = animal.obtenirLigne();
            int colonne = animal.obtenirColonne();

            List<Acteur> zones = jeu.chercherDecorsVoisins(ligne, colonne);
            zones.addAll(jeu.chercherZonesVidesVoisine(ligne, colonne));

            if (!zones.isEmpty()) {
                Random rand = new Random();
                Acteur next = zones.get(rand.nextInt(zones.size()));

                if (next.obtenirType() == Acteur.TYPE_COCOTIER || next.obtenirType() == Acteur.TYPE_PETIT_ROCHER){
                    decors = next.obtenirType();
                } else {
                    decors = -1;
                }

                animal.changerLigne(next.obtenirLigne());
                animal.changerColonne(next.obtenirColonne());
            }
        }

        int saturation = animal.obtenirSaturation();
        if (saturation == 0){
            if (decors == Acteur.TYPE_COCOTIER || decors == Acteur.TYPE_PETIT_ROCHER)
                animal.changerEtat(new SingeAnimalEtatPerche(SingeAnimalEtatAffame.obtenirInstance()));
            else
                animal.changerEtat(SingeAnimalEtatAffame.obtenirInstance());
        } else {
            animal.changerSaturation(saturation - 1);
        }

        if (decors == Acteur.TYPE_COCOTIER || decors == Acteur.TYPE_PETIT_ROCHER)
            animal.changerEtat(new SingeAnimalEtatPerche(SingeAnimalEtatJunkie.obtenirInstance()));
    }

    @Override public void prendreCoup(Animal animal) {
        throw new AnimalEtatException("Pas encore implémenté.");
    }

    @Override
    public String toString() {
        return Ihm.COULEUR_FOND_JAUNE      +
                Ihm.COULEUR_ROUGE           +
                Acteur.SYMBOLE_SINGE         +
                Ihm.COULEUR_REINITIALISATION;
    }
}
