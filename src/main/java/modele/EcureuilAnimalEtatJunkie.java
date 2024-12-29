package modele;

import vue.Ihm;

import java.util.List;
import java.util.Random;

public class EcureuilAnimalEtatJunkie extends AnimalEtat {

    //TODO : Faut t-il le faire décorateur de rassasié ?

    private static EcureuilAnimalEtatJunkie instance;

    private EcureuilAnimalEtatJunkie() {
        super(AnimalEtat.ETAT_JUNKIE);
    }

    /** Obtient l'instance singleton de l'état. */
    public static AnimalEtat obtenirInstance() {
        if (EcureuilAnimalEtatJunkie.instance == null) EcureuilAnimalEtatJunkie.instance = new EcureuilAnimalEtatJunkie();
        return EcureuilAnimalEtatJunkie.instance;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override public void deplacer(Animal animal, Jeu jeu) {

        //TODO: Forcer l'animal à ne pas revenir sur ces pas ?

        faireDeplacement(animal, jeu);
        faireDeplacement(animal, jeu);

        int saturation = animal.obtenirSaturation();
        if (saturation == 0){
            animal.changerEtat(EcureuilAnimalEtatAffame.obtenirInstance());
        } else {
            animal.changerSaturation(saturation - 1);
        }
    }

    @Override public void prendreCoup(Animal animal) {
        throw new AnimalEtatException("Pas encore implémenté.");
    }
    
    @Override
    public String toString() {
        return Ihm.COULEUR_FOND_JAUNE      +
                Ihm.COULEUR_ROUGE           +
                Acteur.SYMBOLE_ECUREUIL      +
                Ihm.COULEUR_REINITIALISATION;
    }

    @SuppressWarnings("DuplicatedCode")
    private void faireDeplacement(Animal animal, Jeu jeu){
        int ligne   = animal.obtenirLigne();
        int colonne = animal.obtenirColonne();

        List<ZoneVide> vides = jeu.chercherZonesVidesVoisine(ligne, colonne);
        if (!vides.isEmpty()) {
            Random rand = new Random();
            Acteur vide = vides.get(rand.nextInt(vides.size()));
            animal.changerLigne(vide.obtenirLigne());
            animal.changerColonne(vide.obtenirColonne());
        }
    }
}
