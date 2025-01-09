package modele;

import vue.Ihm;

import java.util.List;
import java.util.Random;

public class SingeAnimalEtatJunkie extends AnimalEtat {
    public static final String AFFICHAGE = Ihm.COULEUR_FOND_JAUNE + Ihm.COULEUR_ROUGE + Singe.SYMBOLE + Ihm.COULEUR_REINITIALISATION;

    private static SingeAnimalEtatJunkie instance; // Singleton
    private SingeAnimalEtatJunkie() {
        super(AnimalEtat.ETAT_JUNKIE);
    }

    /** Obtient l'instance singleton de l'état. */
    public static AnimalEtat obtenirInstance() {
        if (SingeAnimalEtatJunkie.instance == null) SingeAnimalEtatJunkie.instance = new SingeAnimalEtatJunkie();
        return SingeAnimalEtatJunkie.instance;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void deplacer(Animal animal, Jeu jeu) {
        //TODO: Forcer l'animal à ne pas revenir sur ces pas ?

        for (int i = 0; i < 2; ++i) {

            int ligne   = animal.obtenirLigne();
            int colonne = animal.obtenirColonne();

            List<Acteur> zones = jeu.chercherDecorsVoisins(ligne, colonne);
            zones.addAll(jeu.chercherZonesVidesVoisine(ligne, colonne));

            if (!zones.isEmpty()) {
                Random rand = new Random();
                Acteur next = zones.get(rand.nextInt(zones.size()));

                animal.changerLigne(next.obtenirLigne());
                animal.changerColonne(next.obtenirColonne());
            }
        }

        int saturation = animal.obtenirSaturation();
        if (saturation == 0){
            animal.changerEtat(SingeAnimalEtatAffame.obtenirInstance());
        } else {
            animal.changerSaturation(saturation - 1);
        }

        if (jeu.verifierCaseDecors(animal.obtenirLigne(), animal.obtenirColonne())){
            int type = jeu.obtenirCaseDecors(animal.obtenirLigne(), animal.obtenirColonne()).obtenirType();
            if (type == Acteur.TYPE_COCOTIER){
                animal.changerEtat(new SingeAnimalEtatPerche(animal.obtenirEtat()));
            } else if (type == Acteur.TYPE_PETIT_ROCHER){
                animal.changerEtat(new SingeAnimalEtatCache(animal.obtenirEtat()));
            }
        }
    }

    @Override
    public void prendreCoup(Animal animal) {
        throw new AnimalEtatException("Pas encore implémenté.");
    }

    @Override
    public String toString() {
        return SingeAnimalEtatJunkie.AFFICHAGE;
    }
}
