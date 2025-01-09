package modele;

import vue.Ihm;

import java.util.HashMap;
import java.util.Map;

public class SingeAnimalEtatEffraye extends AnimalEtat{

    public static final String AFFICHAGE = Ihm.COULEUR_FOND_JAUNE + Ecureuil.SYMBOLE + Ihm.COULEUR_REINITIALISATION;
    private Map<Animal, Integer> tourEffrayer;

    private static SingeAnimalEtatEffraye instance;

    private SingeAnimalEtatEffraye(){
        super(AnimalEtat.ETAT_EFFRAYE);
        tourEffrayer = new HashMap<Animal, Integer>();
    }

    public static AnimalEtat obtenirInstance(){
        if (instance == null)
            instance = new SingeAnimalEtatEffraye();
        return instance;
    }

    @Override
    public void deplacer(Animal animal, Jeu jeu) {
        int ligne = animal.obtenirLigne();
        int colonne = animal.obtenirColonne();

        if (tourEffrayer.containsKey(animal)){
            tourEffrayer.put(animal, tourEffrayer.get(animal) - 1);
            if (tourEffrayer.get(animal) <= 0) {
                tourEffrayer.remove(animal);
                if(animal.obtenirSaturation() == 0){
                    animal.changerEtat(SingeAnimalEtatAffame.obtenirInstance());
                } else {
                    animal.changerEtat(SingeAnimalEtatRassasie.obtenirInstance());
                }
            }
        } else {
            tourEffrayer.put(animal, 2);
        }
        if (animal.obtenirSaturation() > 0){
            animal.changerSaturation(animal.obtenirSaturation() - 1);
        }

        if (jeu.verifierCaseDecors(ligne, colonne)){
            int type = jeu.obtenirCaseDecors(ligne, colonne).obtenirType();
            if (type == Acteur.TYPE_COCOTIER){
                animal.changerEtat(new SingeAnimalEtatPerche(animal.obtenirEtat()));
            } else if (type == Acteur.TYPE_PETIT_ROCHER){
                animal.changerEtat(new SingeAnimalEtatCache(animal.obtenirEtat()));
            }
        }
    }

    @Override
    public void prendreCoup(Animal animal){
        throw new AnimalEtatException("Comportement non-spécifié.");
    }


    @Override
    public String toString() {
        return AFFICHAGE;
    }
}
