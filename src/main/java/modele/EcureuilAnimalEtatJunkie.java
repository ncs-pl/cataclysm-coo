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

        int ligne   = animal.obtenirLigne();
        int colonne = animal.obtenirColonne();

        int decors = -1;

        for (int i = 0; i < 2; ++i){

            List<Acteur> zones = jeu.chercherDecorsVoisins(ligne, colonne);
            zones.addAll(jeu.chercherZonesVidesVoisine(ligne, colonne));

            if (!zones.isEmpty()) {
                Random rand = new Random();
                Acteur next = zones.get(rand.nextInt(zones.size()));

                if (next.obtenirType() == Acteur.TYPE_ARBRE || next.obtenirType() == Acteur.TYPE_BUISSON){
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
            if(decors == Acteur.TYPE_ARBRE)
                animal.changerEtat(new EcureuilAnimalEtatPerche(EcureuilAnimalEtatAffame.obtenirInstance()));
            else if (decors == Acteur.TYPE_BUISSON)
                animal.changerEtat(new EcureuilAnimalEtatCache(EcureuilAnimalEtatAffame.obtenirInstance()));
            else
                animal.changerEtat(EcureuilAnimalEtatAffame.obtenirInstance());
        } else {
            animal.changerSaturation(saturation - 1);
        }

        if(decors == Acteur.TYPE_ARBRE)
            animal.changerEtat(new EcureuilAnimalEtatPerche(EcureuilAnimalEtatJunkie.obtenirInstance()));
        else if (decors == Acteur.TYPE_BUISSON)
            animal.changerEtat(new EcureuilAnimalEtatCache(EcureuilAnimalEtatJunkie.obtenirInstance()));
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
}
